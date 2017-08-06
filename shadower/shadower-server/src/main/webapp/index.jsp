<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Shadower</title>

<link href="css/span.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap-3.3.7.min.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
#tool {
	margin-top: 20px;
	margin-left: 20px;
}

#tool .input-group {
	width: 300px;
	float: left;
}

#tool .btn-group {
	margin-left: 10px;
}

#container {
	margin-top: 20px;
	margin-left: 20px;
	margin-right: 20px;
}
</style>

</head>
<body>

	<div id="tool">
		<div class="input-group">
			<span class="input-group-addon">traceId</span> <input type="text"
				class="form-control" name="traceId" id="traceIdTxt">
		</div>
		<div class="btn-group">
			<button type="button" class="btn btn-success" id="queryBtn">query</button>
			<button type="button" class="btn btn-success" id="jsonBtn">json</button>
		</div>

	</div>


	<!-- 模态框（Modal） -->
	<div class="modal fade" id="jsonModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<div id="container"></div>

	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-3.3.7.min.js"></script>
	<script type="text/javascript" src="js/utils/common.js"></script>
	<script type="text/javascript" src="js/utils/jsonUtil.js"></script>
	<script type="text/javascript" src="js/utils/spanUtil.js"></script>
	<script type="text/javascript" src="js/index.js"></script>

	<script type="text/javascript">
		
	</script>
</body>
</html>