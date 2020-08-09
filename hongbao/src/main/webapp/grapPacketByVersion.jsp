<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>模拟并发抢红包 --乐观锁(无重入)</title>
    <script src="js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //模拟1200个异步请求，进行并发
            var max = 10000;
            for (var i = 0; i < max; i++) {
                $.ajax({
                    url: "./userRedPacket/grapRedPacketForVersion.do",
                    type: 'get',
                    data: {redPacketId: "4", userId: i},
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
