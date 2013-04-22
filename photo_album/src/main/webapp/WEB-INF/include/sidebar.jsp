<div class="left">
	<ul>
		<security:authorize ifAllGranted="ROLE_SUPERVISOR">
			<li>Administration
				<ul>
					<li><a href="viewAllUsers.htm">user list</a></li>
					<li><a href="newUser.htm">new user</a></li>
				</ul>
		</security:authorize>

		<security:authorize ifAllGranted="ROLE_USER">
			<li>Administration
				<ul>
					<li><a href="editUser.htm?userId=${User.userId}">edit</a></li>
					<li><a href="addFriend.htm">add friend</a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize ifAllGranted="ROLE_USER">
			<li>Album
				<ul>
					<li><a href="allUserPhotoAlbums.htm">album list</a></li>
					<li><a href="addUserPhotoAlbum.htm">add album</a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>