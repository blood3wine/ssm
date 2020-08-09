<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>模拟并发抢红包</title>
    <!-- 加载Query文件-->
    <script src="js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //模拟异步请求，进行并发
            var max = 10000;
            for (var i = 0; i < max; i++) {
                $.ajax({
                    url: "./userRedPacket/grapRedPacket.do",
                    type: 'get',
                    data: {redPacketId: "2", userId: i},
                    //成功回调
                    success: function (result) {

                    }
                })
            }
        });
    </script>
</head>
<body>

</body>
</html>
