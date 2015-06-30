/**
 * Created by WhatTheFeeK on 29/6/15.
 */
$(function () {

    HTTPbase = '<%= Settings.NodeHTTPBase %>';
    WSbase = '<%= Settings.NodeWSBase %>';

    var dispatcher;
    var channel;
    var heartbeat_timer, queue_check_timer;
    var heartbeat_period = 5000; //milliseconds
    var queue_check_period = 1000; //milliseconds

    ajax_node = function () {
        console.log('Starting AJAX Node.');

        register_success = function (data) {
            console.log('Register Success');
            heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);
            queue_check_timer = setTimeout(check_queue, queue_check_period);

        };

        register_failure = function (data) {
            console.log('Register Failure:' + data.message);
            setTimeout(register_node, 5000);
        };

        register_node = function (data) {
            var node = {
                uuid: $.cookie('uuid')
            };

            $.post("/ajax/register", {}, 'json')
                .done(register_success)
                .error(register_failure);
        };

        register_node();

        store_block = function (data) {
            console.log('Storing data for block ' + data.block_id);
            localStorage[data.block_id] = data.block_data;
        };

        send_block = function (block_id) {
            console.log('Sending data for block ' + block_id);
            var block = {
                block_id: block_id,
                block_data: localStorage[block_id]
            };

            $.post("/ajax/block_upload", block, 'json');
        };

        send_heartbeat = function () {
            blocks = []

            for (var key in localStorage) {
                if (key.indexOf('fileblock') == 0) {
                    var block = {
                        block_id: key,
                        md5: $.md5(localStorage[key])
                    };

                    blocks.push(block);
                }
            }

            $.post("/ajax/heartbeat", {blocks: blocks}, 'json')
                .done(clear_blocks);
            heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);
        };

        clear_blocks = function (data) {
            for (var i = 0; i < data.to_delete.length; i++) {
                console.log('Clearing block ' + data.to_delete[i]);
                localStorage.removeItem(data.to_delete[i]);
            }
        };

        check_queue = function () {
            $.post("/ajax/check_queue", {}, 'json')
                .done(function (data) {
                    for (var i = 0; i < data.to_send.length; i++) {
                        send_block(data.to_send[i])
                    }

                    for (var i = 0; i < data.to_store.length; i++) {
                        store_block(data.to_store[i])
                    }
                });

            queue_check_timer = setTimeout(check_queue, queue_check_period);
        };

    }

    ws_node = function (data) {
        console.log('Starting WS Node.');

        var node = {
            uuid: $.cookie('uuid')
        };

        register_success = function (data) {
            console.log('Register Success');

            //save the UUID
            $.cookie('uuid', data.uuid)

            // subscribe to the channel
            channel = dispatcher.subscribe(data.uuid);
            channel.bind('store_block', store_block);
            channel.bind('send_block', send_block);

            heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);

        };

        register_failure = function (data) {
            console.log('Register Failure:' + data.message);
            setTimeout(register_node, 10000);
        };

        dispatcher.trigger('node.register', node, register_success, register_failure);

        store_block = function (data) {
            console.log('Storing data for block ' + data.block_id);
            localStorage[data.block_id] = data.block_data;
        };

        send_block = function (data) {
            console.log('Sending data for block ' + data.block_id);
            var block = {
                block_id: data.block_id,
                block_data: localStorage[data.block_id]
            };

            dispatcher.trigger('block.upload', block);
        };

        send_heartbeat = function () {
            blocks = []

            for (var key in localStorage) {
                if (key.indexOf('fileblock') == 0) {
                    var block = {
                        block_id: key,
                        md5: $.md5(localStorage[key])
                    };

                    blocks.push(block);
                }
            }

            dispatcher.trigger('node.heartbeat', blocks, clear_blocks);
            heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);
        };

        clear_blocks = function (data) {
            for (var index in data) {
                console.log('Clearing block ' + data[index]);
                localStorage.removeItem(data[index]);
            }
        };
    }

    main = function () {
        console.log('JS Loaded.');

        ws_connect = null;


        dispatcher = new WebSocketRails(WSbase + '/websocket');
        dispatcher.on_open = ws_node;
        setTimeout(function () {
            if (dispatcher.state != 'connected') {
                ajax_node();
            }
        }, 2000);

    }

    if (localStorage) {
        main();
    }

});

