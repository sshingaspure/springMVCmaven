<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Case</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
		<h2>Stock Trading</h2>
		<p>Click on tab to buy or sell stocks</p>
		<p id="errorMessage" style="color:green;">${message}</p>
		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#buy">Buy
					Stock</a></li>
			<li><a data-toggle="tab" href="#sell">Sell Stock</a></li>
		</ul>

		<div class="tab-content">
			<div id="buy" class="tab-pane fade in active">
				<h3>Buy stock</h3>

				<form id="buyForm" action="buyStocksForCompany" method="post">
					<table>
						<tr>
							<td>Select Company</td>
							<td><select id="companyName" name="cmpname">
									<option value="0">select a company</option>
									<c:forEach items="${companyList}" var="company">
					console.log("${company.cmp_name}");
						<option
											value="${company.cmp_id},${company.cmp_name},${company.share_value},${customer.customerId}">${company.cmp_name}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td>Selected Company</td>
							<td>Share Value</td>
						</tr>
						<tr>
							<td>Select Number of shares:</td>
							<td><input type="text" id="numOfSharestoBuy" name="numofshare"/></td>
							<td><input type="text" id="cmpName" disabled /></td>
							<td><input type="text" id="shareValue" disabled /></td>
							<td><input type="text" id="numOfShare" disabled /></td>
						</tr>

						<tr>
							<td><button type="button" onclick="checkBuyCondition()">Buy Shares</button></td>
						
						</tr>

					</table>

				</form>

				<script type="text/javascript"
					src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
				<script>
					var mytextbox = document.getElementById('cmpName');
					var mydropdown = document.getElementById('companyName');
					var shareValue = document.getElementById('shareValue');
					var numOfShare = document.getElementById('numOfShare');
					mydropdown.onchange = function() {
						var strUser = mydropdown.options[mydropdown.selectedIndex].value;
						if (strUser == 0) //for text use if(strUser1=="Select")
						{
							mytextbox.value = "";
							shareValue.value = "";
							alert("Please select a company");
						} else {
							console.log(this.value);
							var ss = this.value.split(',');
							mytextbox.value = ss[1]; //to appened
							shareValue.value = ss[2];

							console.log("Result: " + ss[3]);
							var cust_id = ss[3];
							var cmp_id = ss[0];
							console.log("Result: " + ss[3]);
							//numOfShare.value=getNumberOfShares(cust_id,cmp_id);
							var inputData = {
								"custid" : cust_id,
								"cmpid" : cmp_id
							}
							$.ajax({
								type : "GET",
								/*contentType : 'application/json; charset=utf-8',*///use Default contentType
								dataType : 'json',
								url : "getNumberOfShares",
								data : inputData,
								success : function(result) {
									console.log("Result from ajax" + result);
									numOfShare.value = result;

								}
							});

						}//mytextbox.innerHTML = this.value;
					}

					function checkBuyCondition(){
						console.log("inside checkBuyCondition");
						var errorMessage=document.getElementById("errorMessage");
						var cmpName = document.getElementById('companyName');
						var strUser = cmpName.options[mydropdown.selectedIndex].value;
						if (strUser==0) {
							console.log("Please select company name from drop down list.");
							var str='Please select company name from drop down list.';
							var result = str.fontcolor("red");
							document.getElementById("errorMessage").innerHTML = result;
							
							return;
						}
						var numOfSharestoBuy=document.getElementById("numOfSharestoBuy").value;
						if (numOfSharestoBuy <= 0) {
							console.log("Please enter valid number of shares.");
							var str='Please valid number of shares';
							var result = str.fontcolor("red");
							document.getElementById("errorMessage").innerHTML = result;
							return;
						}
						
						var shareValue=document.getElementById('shareValue').value;
						var amountRequired=shareValue*numOfSharestoBuy;
						var balanceAvailable=${customer.balance};
						console.log("inside checkBuyCondition amountRequired: "+amountRequired);
						console.log("inside checkBuyCondition balanceAvailable: "+balanceAvailable);
						if (balanceAvailable < amountRequired) {
							console.log("inside if in checkBuyCondition");
							//errorMessage.value="Sorry, you do not have sufficient balance. Available balance: "+balanceAvailable+" Required Balance: "+amountRequired;
							var str="Sorry, you do not have sufficient balance. Available balance: "+balanceAvailable+" Required Balance: "+amountRequired;
							var result = str.fontcolor("red");
							document.getElementById("errorMessage").innerHTML = result;
							//$('#errorMessage').text('Sorry, you do not have sufficient balance. Available balance: '+balanceAvailable+' Required Balance: '+amountRequired);
							return;
						}
						
						document.getElementById("buyForm").submit();
					}
				</script>
			
			<table>
		<tr>
			<td><a href="viewStock">Check Stock</a></td>
			<td><a href="buySellStock">Buy or Sell Stock</a></td>
			<td><a href="viewCompanies">View Companies</a></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td><a href="logout">Logout</a></td>
		</tr>

	</table>

			</div>
			<div id="sell" class="tab-pane fade">
				<h3>Sell stock</h3>
				<form id="sellForm" action="sellStocksForCompany" method="post">
				<table>
					<tr>
						<td>Select Company</td>
						<td><select id="companyName1" name="cmpName1">
								<option value="0">select a company</option>
								<c:forEach items="${companyList}" var="company">
					console.log("${company.cmp_name}");
						<option
										value="${company.cmp_id},${company.cmp_name},${company.share_value},${customer.customerId}">${company.cmp_name}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td>Selected Company</td>
						<td>Share Value</td>
					</tr>
					<tr>
						<td>Select Number of shares:</td>
						<td><input type="text" id="numOfSharesToSellId" name="numOfSharesToSell" /></td>
						<td><input type="text" id="cmpName1" disabled /></td>
						<td><input type="text" id="shareValue1" disabled /></td>
						<td><input type="text" id="numOfShare1" disabled /></td>
					</tr>

					<tr>
						
						<td><button type="button" onclick="checkSellCondition()">Sell Shares</button></td>
					
					</tr>

					</table>
				</form>
				<script type="text/javascript"
					src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
				<script>
					var mytextbox1 = document.getElementById('cmpName1');
					var mydropdown1 = document.getElementById('companyName1');
					var shareValue1 = document.getElementById('shareValue1');
					var numOfShare1 = document.getElementById('numOfShare1');
					mydropdown1.onchange = function() {
						var strUser = mydropdown1.options[mydropdown1.selectedIndex].value;
						if (strUser == 0) //for text use if(strUser1=="Select")
						{
							mytextbox1.value = "";
							shareValue1.value = "";
							alert("Please select a company");
						} else {
							var ss = this.value.split(',');
							mytextbox1.value = ss[1]; //to appened
							shareValue1.value = ss[2];

							console.log("Result: " + ss[3]);
							var cust_id = ss[3];
							var cmp_id = ss[0];
							console.log("Result: " + ss[3]);
							//numOfShare.value=getNumberOfShares(cust_id,cmp_id);
							var inputData = {
								"custid" : cust_id,
								"cmpid" : cmp_id
							}
							$.ajax({
								type : "GET",
								/*contentType : 'application/json; charset=utf-8',*///use Default contentType
								dataType : 'json',
								url : "getNumberOfShares",
								data : inputData,
								success : function(result) {
									console.log("Result" + result);
									numOfShare1.value = result;

								}
							});

						}//mytextbox.innerHTML = this.value;
					}
					
					function checkSellCondition(){
						console.log("inside checkSellCondition");
						var errorMessage=document.getElementById("errorMessage");
						var cmpName = document.getElementById('companyName1');
						var strUser = cmpName.options[mydropdown1.selectedIndex].value;
						if (strUser==0) {
							console.log("Please select company name from drop down list.");
							var str='Please select company name from drop down list.';
							var result = str.fontcolor("red");
							document.getElementById("errorMessage").innerHTML = result;
							
							return;
						}
						var numOfSharestoSell=document.getElementById("numOfSharesToSellId").value;
						if (numOfSharestoSell <= 0) {
							console.log("Please enter valid number of shares.");
							var str='Please enter valid number of shares';
							var result = str.fontcolor("red");
							document.getElementById("errorMessage").innerHTML = result;
							return;
						}
						
						
						var numOfSharesUHave=document.getElementById('numOfShare1').value;
						if (numOfSharesUHave < numOfSharestoSell) {
							var str='Number of shares entered is less than what you have. You have only '+numOfSharesUHave+' shares.';
							var result = str.fontcolor("red");
							document.getElementById("errorMessage").innerHTML = result;
							return;
						} 
						
					 document.getElementById("sellForm").submit();
					}
					
				</script>

				<table>
		<tr>
			<td><a href="viewStock">Check Stock</a></td>
			<td><a href="buySellStock">Buy or Sell Stock</a></td>
			<td><a href="viewCompanies">View Companies</a></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td><a href="logout">Logout</a></td>
		</tr>

	</table>
			</div>
		</div>
	</div>

</body>
</html>
