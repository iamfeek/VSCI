/**
 * Created by WhatTheFeeK on 29/6/15.
 */
var dispatcher;
var channel;
var heartbeat_timer, queue_check_timer;
var heartbeat_period = 5000; //milliseconds
var queue_check_period = 1000; //milliseconds

ajax_node = function () {
    console.log("Starting AJAX Node")

    register_success = function (data) {
        if (data === "wb") {
            console.log("Welcome Back!");
        } else {
            console.log("Register Success")
            localStorage.setItem("uuid", data);
        }


        heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);
        queue_check_timer = setTimeout(check_queue, queue_check_period);
    };

    register_failure = function (data) {
        console.log('Register Failure:' + data.message);
        setTimeout(register_node, 5000);
    };

    register_node = function (data) {
        //var node = {
        //    uuid: $.cookie('uuid')
        //};

        for (var key in localStorage) {
            var uuid = localStorage.getItem("uuid");
        }


        $.post("node-register", {uuid: uuid}, 'json')
            .done(register_success)
            .error(register_failure);
    };

    register_node();

    send_heartbeat = function () {
        //blocks = []
        //
        //for (var key in localStorage) {
        //    if (key.indexOf('fileblock') == 0) {
        //        var block = {
        //            block_id: key,
        //            md5: $.md5(localStorage[key])
        //        };
        //
        //        blocks.push(block);
        //    }
        //}
        //
        //$.post("node-heartbeat", {blocks: blocks}, 'json')
        //    .done(clear_blocks);
        //heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);

        for (var key in localStorage) {
            var uuid = localStorage.getItem("uuid");
        }

        $.post("node-heartbeat", {uuid: uuid}, 'json')
            .done(function (data) {
                console.log(data);
            });

        heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);
    };

    clear_blocks = function (data) {
        //for (var i = 0; i < data.to_delete.length; i++) {
        //    console.log('Clearing block ' + data.to_delete[i]);
        //    localStorage.removeItem(data.to_delete[i]);
        //}

        console.log("clear_blocks");
    };

    check_queue = function () {
        $.post("node-check-queue", {}, 'json')
            .done(function (data) {
                //for (var i = 0; i < data.to_send.length; i++) {
                //    send_block(data.to_send[i])
                //}
                //
                //for (var i = 0; i < data.to_store.length; i++) {
                //    store_block(data.to_store[i])
                //}

                console.log(data);
            });

        queue_check_timer = setTimeout(check_queue, queue_check_period);
    };
}

test = function () {
    return "Result: 1231";
}

main = function () {
    setTimeout(ajax_node, 2000);
}

if (localStorage) {
    main();
}

