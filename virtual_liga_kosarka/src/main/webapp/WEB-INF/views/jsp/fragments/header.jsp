<head>
<title>Kosarkaska liga</title>
<spring:url value="/resources/images/basketball1.jpg" var="basketball1" />
<spring:url value="/resources/images/lopta.png" var="lopta" />
<link rel="icon" href="${lopta}">
<spring:url value="/resources/bootstrap/css/bootstrap.min.css"
	var="bootstrapMinCss" />
<link href="${bootstrapMinCss}" rel="stylesheet" />
<spring:url value="/resources/bootstrap/js/jquery-1.11.3.min.js"
	var="jQuery" />
<script type="text/javascript"
    src="${jQuery}">
    </script>
<meta charset="UTF-8">
</head>
<style type="text/css">
body {
	background-color: white;
	margin-left: 7%;
	margin-right: 7%;
}

.tablePodaci {
	border-collapse: separate;
	border-spacing: 10px;
	width:40%
}

.mid {
	float: left;
	position: relative;
	left: 30%;
}

.midL {
	float: left;
	position: relative;
	left: 25%;
}
.midLL {
	float: left;
	position: relative;
	left: 18%;
}

.midLD {
	float: left;
	position: relative;
	left: 28%;
}

.inputForBox {
	width: 300px;
}

.inputForKonf {
	width: 100px;
}

.inputDropdown {
	width: 500px;
}

a.disabled {
   pointer-events: none;
   cursor: default;
}

footer {

  position: absolute;
  bottom: 0;
  width: 100%;
  height: 60px;
  background-color: #f5f5f5;
}

</style>

<body>

	<nav class="navbar navbar-default">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar"
					data-aria-expanded="false" data-aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

				<!-- 				<ul class="nav navbar-nav"> -->
				<!-- 					<li class="dropdown"><a href="#" class="dropdown-toggle" -->
				<!-- 						data-toggle="dropdown" data-role="button" -->
				<!-- 						data-aria-haspopup="true" data-aria-expanded="false">Virtualna -->
				<!-- 							liga<span class="caret"></span> -->
				<!-- 					</a> -->
				<!-- 						<ul class="dropdown-menu"> -->
				<!-- 							<li><a href="#">Moj virtualni tim</a></li> -->
				<!-- 							<li><a href="#">Rezultati utakmica</a></li> -->
				<!-- 							<li><a href="#">Poredak</a></li> -->
				<!-- 						</ul></li> -->
				<!-- 				</ul> -->
				<ul class="nav navbar-nav">
					<li><a href="/virtual_liga_kosarka/">Poredak</a></li>
					<li><a href="/virtual_liga_kosarka/osobnaStranica">Osobna
							stranica</a></li>
				</ul>
			</div>
			<div id="navbar" class="navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><c:choose>
							<c:when test="${empty username}">
								<p style="font-style: italic" class="navbar-text">Niste
									prijavljeni.</p>
							</c:when>
							<c:otherwise>
								<p style="font-style: italic" class="navbar-text">Dobrodosli,
									${username}!</p>
							</c:otherwise>
						</c:choose></li>
					<li><a href="/virtual_liga_kosarka/prijava"
						style="color: blue">Prijava</a></li>
					<li><a href="/virtual_liga_kosarka/registracija"
						style="color: blue">Registracija</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="jumbotron">
		<div class="container">
			<img class="center-block" src="${basketball1}">
		</div>
	</div>