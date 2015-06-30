<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<html>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>

    <title>VSCI | Dashboard</title>

    <script>
        // wait for the DOM to be loaded
        $(document).ready(function () {
            var options = {
                target: '#output',
                success: afterSuccess,
                uploadProgress: OnProgress //upload progress callback
            }

            function OnProgress(event, position, total, percentComplete) {
                //Progress bar
                $('#progressbox').show();
                $('#progressbar').width(percentComplete + '%') //update progressbar percent complete
                $('#statustxt').html(percentComplete + '%'); //update status text
                if (percentComplete > 50) {
                    $('#statustxt').css('color', '#000'); //change status text to white after 50%
                }
            }

            function afterSuccess() {
                alert("Done! Uploaded!");
            }

            $('#myForm').ajaxForm(options);
        });
    </script>
</head>
<body>
<h1>Upload File</h1>

<form id="myForm" method="POST" action="UploadServlet" enctype="multipart/form-data">
    File Name:
    <input type="text" placeholder="File Name" value="file" name="fileName"/><br/><br/>

    Enter your password to encrypt the file: (16 characters max!)
    <input type="password" value="/tmp" name="destination"/><br/><br/>

    Select file to upload:
    <input type="file" name="dataFile" id="fileChooser"/><br/><br/>

    <input type="submit" value="Upload"/>

</form>
<div id="progressbox">
    <div id="progressbar"></div>
    <div id="statustxt">0%</div>
</div>
<div id="output"></div>
</body>
</html>
