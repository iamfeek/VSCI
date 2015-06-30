<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>HTML5/Web/DOM Storage Demonstrated</title>
    <script language="javascript">

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

            jQuery.post("/ajax/heartbeat", {blocks: blocks}, 'json')
                    .done(clear_blocks);
            heartbeat_timer = setTimeout(send_heartbeat, heartbeat_period);
        };
        /*
         * Indicate if this browser supports local storage.
         */
        function html5StorageSupported() {
            return ('localStorage' in window) && window['localStorage'] !== null;
        }
        /*
         * Provide number of elements in storage.
         */
        function determineNumberStoredElements() {
            return html5StorageSupported() ? localStorage.length : "Not Applicable";
        }
        /*
         * Provide indication on web page of whether this browser supports local storage.
         */
        function initialize() {
            document.form1.supported.value = html5StorageSupported() ? "Yes!" : "No (:";
            document.form1.numStored.value = determineNumberStoredElements();
        }
        /*
         * Save favorite movies to local storage.
         */
        function persistFavoriteMovies() {
            if (html5StorageSupported()) {
                for (var i = 1; i <= 10; i++) {
                    var movie = document.form1["movie" + i].value;
                    var storageIndex = "rmoug.td2011.movie." + i;
                    //alert("Movie #" + i + " [" + storageIndex+ "]: " + movie);
                    localStorage[storageIndex] = movie;
                }
                document.form1.numStored.value = determineNumberStoredElements();
            }
            else {
                alert("Cannot save to local storage because it's not supported.");
            }
        }
        /*
         * Load favorite movies from local storage.
         */
        function loadFavoriteMovies() {
            if (html5StorageSupported()) {
                for (var i = 1; i <= 10; i++) {
                    document.form1["movie" + i].value = localStorage["rmoug.td2011.movie." + i];
                }
            }
        }
        /*
         * Clear favorite movies from both local storage and from form fields.
         */
        function clearFavoriteMovies() {
            if (html5StorageSupported()) {
                // Clear favorite movies from local storage
                localStorage.clear();

                // Clear fields of movies content
                for (var i = 1; i <= 10; i++) {
                    document.form1["movie" + i].value = null;
                }
                document.form1.numStored.value = determineNumberStoredElements();
            }
        }
    </script>
</head>

<body onload="initialize()">

<h1>HTML5/Web/DOM Storage</h1>

<form name="form1">
    <table>
        <tr>
            <td>Does this browser support local storage?</td>
            <td><input type="text" name="supported"></td>
        </tr>
        <tr>
            <td>Number of Stored Elements</td>
            <td><input type="text" name="numStored"></td>
        </tr>
    </table>
    <table>
        <tr>
            <td colspan="2" style="font-weight: bold;" align="center">Favorite Movies</td>
        </tr>
        <tr>
            <td>#1</td>
            <td><input type="text" name="movie1"></td>
        </tr>
        <tr>
            <td>#2</td>
            <td><input type="text" name="movie2"></td>
        </tr>
        <tr>
            <td>#3</td>
            <td><input type="text" name="movie3"></td>
        </tr>
        <tr>
            <td>#4</td>
            <td><input type="text" name="movie4"></td>
        </tr>
        <tr>
            <td>#5</td>
            <td><input type="text" name="movie5"></td>
        </tr>
        <tr>
            <td>#6</td>
            <td><input type="text" name="movie6"></td>
        </tr>
        <tr>
            <td>#7</td>
            <td><input type="text" name="movie7"></td>
        </tr>
        <tr>
            <td>#8</td>
            <td><input type="text" name="movie8"></td>
        </tr>
        <tr>
            <td>#9</td>
            <td><input type="text" name="movie9"></td>
        </tr>
        <tr>
            <td>#10</td>
            <td><input type="text" name="movie10"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="button" value="Load" onclick="loadFavoriteMovies()">
                <input type="button" value="Save" onclick="persistFavoriteMovies()">
                <input type="button" value="Clear" onclick="clearFavoriteMovies()">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
