<h1>Registration</h1>
<p>This is the Registration Page!</p>
<p>${loggedIn ? "And you're logged in" : "And you're NOT logged in"}</p>
<p>${messages.alert}</p>

<form name="registration" action="RegisterUser" method="POST">
	<ul>
		<li><span>Nickname: </span><input type="text" name="nickname" value="${nickname}" /><c:choose><c:when test="${!empty messages && messages.containsKey('nickname')}"><span>${messages.nickname}</span></c:when></c:choose></li>
		<li><span>First name: </span><input type="text" name="first_name" value="${first_name}" /><c:choose><c:when test="${!empty messages && messages.containsKey('first_name')}"><span>${messages.first_name}</span></c:when></c:choose></li>
		<li><span>Last name: </span><input type="text" name="last_name" value="${last_name}" /><c:choose><c:when test="${!empty messages && messages.containsKey('last_name')}"><span>${messages.last_name}</span></c:when></c:choose></li>
		<li><span>e-mail: </span><input type="text" name="email" value="${email}" /><c:choose><c:when test="${!empty messages && messages.containsKey('email')}"><span>${messages.email}</span></c:when></c:choose></li>
		<li><span>e-mail confimation: </span><input type="text" name="email_confirmation" value="${email_confirmation}" /><c:choose><c:when test="${!empty messages && messages.containsKey('email_confirmation')}"><span>${messages.email_confirmation}</span></c:when></c:choose></li>
		<li><span>password: </span><input type="password" name="password" value="${password}" /><c:choose><c:when test="${!empty messages && messages.containsKey('password')}"><span>${messages.password}</span></c:when></c:choose></li>
		<li><span>password confirmation: </span><input type="password" name="password_confirmation" value="${password_confirmation}" /><c:choose><c:when test="${!empty messages && messages.containsKey('password_confirmation')}"><span>${messages.password_confirmation}</span></c:when></c:choose></li>
		<li><span>birth date: </span><input type="date" name="birth_date" min="1900-01-01" max="2015-01-01" value="${birth_date}" /><c:choose><c:when test="${!empty messages && messages.containsKey('birth_date')}"><span>${messages.birth_date}</span></c:when></c:choose></li>
		<li><input type="checkbox" name="optin" ${optin ? 'checked="checked"' : ''}<span>Do you want to receive newsletters?</span></li>
		<li>
			<select name="gender">
				<option value="male" ${gender ? 'selected="selected"' : ''}>male</option>
				<option value="female" ${!gender ? 'selected="selected"' : ''}>female</option>
			</select>
			<c:choose><c:when test="${!empty messages && messages.containsKey('gender')}"><span>${messages.gender}</span></c:when></c:choose>
		</li>
		<input type="submit" />
	</ul>
</form>