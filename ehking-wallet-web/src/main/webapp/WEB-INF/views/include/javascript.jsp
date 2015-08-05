<script type="text/javascript" src="${staticWebUrl}/js/seajs/sea.js"></script>
<script type="text/javascript" src="${staticWebUrl}/cashier/${staticWebSeajsConfig}"></script>
<script>
    seajs.use("jquery", function(jQuery){
        window.$ = window.jQuery = jQuery;
        window.ctx = "${pageContext.request.contextPath}";
    });
</script>
