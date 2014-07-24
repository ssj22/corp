<!doctype html>
<html>
    <head>
        <title>Hotmix PMC</title>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="resources/css/bootstrap.min.css">
		<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
		<script src="resources/js/jquery-1.9.1.js"></script>
	    <script src="resources/js/jquery-ui.js"></script>
        <script type="text/javascript" src="resources/js/jquery.min.js"></script>
        <script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
        <style type="text/css">
            body {
                padding-top: 50px;
            }
        </style>
		<script>
			$(document).on("click","a[id='materialImg']", function (e) {
				$('#myTab li:eq(1) a').tab('show')
			});

			$(document).on("click","a[id='setupImg']", function (e) {
				$('#myTab li:eq(3) a').tab('show')
			});

			$(document).on("click","a[id='reportImg']", function (e) {
				$('#myTab li:eq(2) a').tab('show')
			});
		</script>
    </head>
    
    <body>
		<div class="navbar navbar-default navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                    </button><a class="navbar-brand" href="#">Hotmix</a>
                </div>
				<div class="navbar-right">
					<img src="img/symbol2.gif">
				</div>
				<div class="navbar-right">
					<font style="font-family:verdana; font-size: 11px;"><i>Welcome</i> <b>Sushil Joshi</b> !</font>
				</div>
			</div>
		</div>
        <div class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav nav-tabs" id="myTab">
                        <li class="active"><a href="#home" data-toggle="tab">Home</a></li>
                        <li><a href="#material" data-toggle="tab">Materials</a></li>
                        <li><a href="#report" data-toggle="tab">Reports</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Setup <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#centerPanel">Users</a></li>
                                <li><a href="#centerPanel">Sections</a></li>
                                <li><a href="#centerPanel">Vehicles</a></li>
								<li><a href="#centerPanel">Materials</a></li>
                                <li class="divider"></li>
                                <li class="dropdown-header">Nav header</li>
                                <li><a href="#">Contact Admin</a></li>
                            </ul>
                        </li>
                    </ul>
					<form class="navbar-form navbar-right">
                        <div class="form-group">
                            <input type="text" placeholder="Enter Text" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Search</button>
                    </form>
				</div><!--/.navbar-collapse -->
            </div>
        </div><!-- Main jumbotron for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div class="container tab-content">
				<div class="tab-pane fade active in" id="home">
					<div id="topPanel">
					  <div id="headerPanelfirst">
						<h2>Materials</h2>
						<p>View, create and update Material entries here</p>
						<a href="#material" id="materialImg">&nbsp;</a>
					</div>
					  <div id="headerPanelsecond">
						<h2>Reports</h2>
						<p>View various Reports </p>
						<a href="#setup" id="reportImg">&nbsp;</a></div>
					  <div id="headerPanelthird">
						<h2>Setup</h2>
						<p>Setup an entity </p>
						<a href="#report" id="setupImg">&nbsp;</a></div>
					</div>
				  </div>
				  <div class="tab-pane fade" id="material">
					<div id="topPanel">
						<table class="table table-striped table-bordered table-hover" id="example" width="100%">
							<thead>
								<tr>
									<th width="30%">Browser</th>
									<th width="20%">Rendering engine</th>
									<th width="18%">Platform(s)</th>
									<th width="20%">Engine version</th>
									<th width="12%">CSS grade</th>
								</tr>
							</thead>
								<tr>
									<td>Browser</th>
									<td>Rendering engine</th>
									<th width="18%">Platform(s)</th>
									<th width="20%">Engine version</th>
									<th width="12%">CSS grade</th>
								</tr>
						</table>
					</div>
				  </div>
				  <div class="tab-pane fade" id="setup">
					<div id="topPanel">
						Setup data here
					</div>
				  </div>
				  <div class="tab-pane fade" id="report">
					<div id="topPanel">
						Report View/Create here
					</div>
				  </div>
				</div>
            </div>
        </div>
        <div class="container">
            <!-- Example row of columns -->
            <div class="row">
                <div class="col-lg-4">
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, sit amet risus. </p>
                </div>
                <div class="col-lg-4">
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, sit amet risus. </p>
                </div>
                <div class="col-lg-4">
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, sit amet risus. </p>
                </div>
            </div>
        </div><!-- /container -->
    </body>

</html>