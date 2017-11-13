<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buy Sell Stocks</title>
</head>
<body>

	<table>
		<tr>
			<td>Select Company</td>
			<td><select id="companyName">
					<option value="0">select a company</option>
					<c:forEach items="${companyList}" var="company">
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
			<td><input type="text" name="numOfShares" /></td>
			<td><input type="text" id="cmpName" disabled /></td>
			<td><input type="text" id="shareValue" disabled /></td>
			<td><input type="text" id="numOfShare" disabled /></td>
		</tr>

		<tr>
			<td><button type="button">Buy Shares</button></td>
			<td></td>
			<td><button type="button">Sell Shares</button></td>
			<td></td>
		</tr>

	</table>

	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
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
				var ss = this.value.split(',');
				mytextbox.value = ss[1]; //to appened
				shareValue.value = ss[2];
				
				console.log("Result: "+ss[3]);
				var cust_id=ss[3];
				var cmp_id=ss[0];
				console.log("Result: "+ss[3]);
				//numOfShare.value=getNumberOfShares(cust_id,cmp_id);
				var inputData = {
					    "custid" :cust_id,
					    "cmpid" : cmp_id
					    }
				 $.ajax({
					    type: "GET",
					    /*contentType : 'application/json; charset=utf-8',*/ //use Default contentType
					    dataType : 'json',
					    url: "getNumberOfShares",
					    data: inputData, 
					    success :function(result) {
					    console.log("Result"+result);
					    numOfShare.value= result;
					    	
					    }
					    });
				
			}//mytextbox.innerHTML = this.value;
		}
		
		function getNumberOfShares(cust_id,cmp_id){
			var number=0;   
			var inputData = {
			    "custid" :cust_id,
			    "cmpid" : cmp_id
			    }
			
			    $.ajax({
			    type: "GET",
			    /*contentType : 'application/json; charset=utf-8',*/ //use Default contentType
			    dataType : 'json',
			    url: "getNumberOfShares",
			    data: inputData, 
			    success :function(result) {
			    console.log("Result"+result);
			    	number= result;
			    	return number;
			    }
			    });
			
			
		}

		
		
	</script>


</body>
</html>