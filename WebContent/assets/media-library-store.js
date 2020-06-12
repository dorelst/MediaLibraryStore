function loadUsers(){
	const urlPath = "http://localhost:8080/MediaLibraryStore/users";
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No users found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}
			
		    const users = JSON.parse(xmlhttp.responseText);
		    const usersTableTop = `<table class="aTable">
		    					<tr>
		    						<th>Id</th><th>First Name</th><th>Last Name</th>
		    						<th>Username</th><th>Users Group</th><th>Classroom Id</th>
		    						<th>Classroom Name</th>
		    					</tr>`;
		    let usersTableContent ="";
		    for (user of users) {
		    	usersTableContent += getUserSummaryInfo(user);
		    }
		    	
		    
		    const usersTableBottom = "</table>";
		    const usersTable = usersTableTop + usersTableContent + usersTableBottom;
		    
		    const sectionMessage = '<div class="section-message">List Of All Users</div>';
		    const content = sectionMessage+usersTable;
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retreive the users info from the server";
		}
	}
    xmlhttp.send();
};

function loadUser(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const userIdInput = document.getElementById("userId").value;
	const urlPath = baseUrl + "/user/" + userIdInput;
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No user selected!") || 
					(xmlhttp.responseText === "Invalid user ID!") ||
					(xmlhttp.responseText.includes("No user with id")) ||
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
				document.getElementById("userId").value = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}
			
		    const user = JSON.parse(xmlhttp.responseText);
		    const userTabletTop = 
		    	`<table class="aTable">
					<tr>
						<th>Id</th><th>First Name</th><th>Last Name</th>
						<th>Username</th><th>Users Group</th><th>Classroom Id</th>
						<th>Classroom Name</th>
					</tr>`;
		    const userTableBottom = "</table>";
		    const userTable = userTabletTop + getUserSummaryInfo(user) + userTableBottom;
		    
			const currentlyBookedTable = getMediaItems(user.currentBookedItems);
			const previouslyBookedTable = getMediaItems(user.previouslyBookedItems);
			
			const sectionMessage = '<div class="section-message">User Info</div>';
			
			const content = sectionMessage + userTable + '<div class="section-message">Media Items Currently booked</div>'
							+ currentlyBookedTable + '<div class="section-message">Media Items Previously booked</div>'
							+ previouslyBookedTable;
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		    document.getElementById("userId").value = "";

		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retreive the user info from the server";
		}
	}
    xmlhttp.send();
};

function getMediaItems (mediaItems) {
    const mediaItemsTableTop = `<table class="aTable">
		<tr>
			<th>Item Type</th><th>Title</th><th>Author First Name</th>
			<th>Author Last Name</th><th>Released Year</th>
		</tr>`;
	let mediaItemsTableContent ="";
	for (mediaItem of mediaItems){
		mediaItemsTableContent += "<tr>"+
			"<td>"+mediaItem.itemType.value+"</td><td>"+mediaItem.title+"</td>";
		if (mediaItem.authorFirstName == null) {
			mediaItemsTableContent += "<td> N/A </td>";
		} else {
			mediaItemsTableContent += "<td>"+mediaItem.authorFirstName+"</td>";
		}
		
		if (mediaItem.authorLastName == null) {
			mediaItemsTableContent += "<td> N/A </td>";
		} else {
			mediaItemsTableContent += "<td>"+mediaItem.authorLastName+"</td>";
		}
		mediaItemsTableContent += "<td>"+mediaItem.releasedYear+"</td>"
		mediaItemsTableContent += "</tr>";
	}
	
	const mediaItemsTableBottom = "</table>";
	const mediaItemsTable = mediaItemsTableTop + mediaItemsTableContent + mediaItemsTableBottom;
	
	return mediaItemsTable;

}

function getUserSummaryInfo(user) {
    let userTableContent ="";
    userTableContent += "<tr>"+
		"<td>"+user.id+"</td><td>"+user.firstName+"</td><td>"+user.lastName+"</td>"+
		"<td>"+user.username+"</td><td>"+user.userGroup.value+"</td>";
    
		let cellIds = "";
		let cellContent = "";
		for (classroom of user.classrooms){
			cellIds += classroom.id + ", ";
			cellContent += classroom.classroomName + ", ";
		}
		cellIds = cellIds.slice(0, -2);
		cellContent = cellContent.slice(0, -2);
		
		userTableContent += "<td>"+cellIds+"</td>"+
		"<td>"+cellContent+"</td>" + "</tr>";
		
    return userTableContent;
}

function loadUsersCurrentlyBooking(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const urlPath = baseUrl + "/userscurrentlybooking";
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No users found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}
			
		    const usersCB = JSON.parse(xmlhttp.responseText);

		    const usersCBTableTop = 
		    	`<table class="aTable">
					<tr>
						<th>Id</th><th>First Name</th><th>Last Name</th>
						<th>Username</th><th>Users Group</th><th>Classroom Id</th>
						<th>Classroom Name</th>
					</tr>`;
			let usersCBTableContent ="";
			for (user of usersCB){
				usersCBTableContent += getUserSummaryInfo(user);
			}

		const usersCBTableBottom = "</table>";
		const usersCBTable = usersCBTableTop + usersCBTableContent + usersCBTableBottom;
		
		const sectionMessage = '<div class="section-message">List Users Currently Booking</div>';
		const content = sectionMessage+usersCBTable;
		
		document.getElementById("mediaLibraryStore").innerHTML = "";
		document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retreive users currenlty booking info from the server";
		}
	}
    xmlhttp.send();
};

function loadClassrooms(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const urlPath = baseUrl + "/classrooms";
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No classrooms found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}
			
		    const classrooms = JSON.parse(xmlhttp.responseText);

		    const classroomsTableTop = `<table class="aTable">
				<tr>
					<th>Id</th><th>Classroom Name</th>
				</tr>`;
		    
			let classroomsTableContent ="";
			for (classroom of classrooms){
				classroomsTableContent += 
				"<tr>"+
					"<td>"+classroom.id+"</td><td>"+classroom.classroomName+"</td>" +
				"</tr>";
			}


			const classroomsTableBottom = "</table>";
			const classroomsTable = classroomsTableTop + classroomsTableContent + classroomsTableBottom;
			
			const sectionMessage = '<div class="section-message">List Of Classrooms</div>';
			const content = sectionMessage + classroomsTable;
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retreive classrooms info from the server";
		}
	}
    xmlhttp.send();
};

function loadClassroomMembers(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const classroomIdInput = document.getElementById("classroomId").value;
	const urlPath = baseUrl + "/classroommembers/" + classroomIdInput;
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No classroom selected!") || 
					(xmlhttp.responseText === "Invalid classroom ID!") ||
					(xmlhttp.responseText.includes("No classroom with id")) ||
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
				document.getElementById("classroomId").value = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}
			const classroomUsers = JSON.parse(xmlhttp.responseText);
			
		    const classroomUsersTableTop = `<table class="aTable">
				<tr>
					<th>Id</th><th>First Name</th><th>Last Name</th>
					<th>Username</th><th>Users Group</th><th>Classroom Id</th>
					<th>Classroom Name</th>
				</tr>`;
			let classroomUsersTableContent = "";
			for (classroomUser of classroomUsers) {
				classroomUsersTableContent += getUserSummaryInfo(classroomUser);
			}
				
			const classroomUsersTableBottom = "</table>";
			
			const classroomUsersTable = classroomUsersTableTop + classroomUsersTableContent + classroomUsersTableBottom;
			
			const sectionMessage = '<div class="section-message">List Of Classroom Users</div>';
			const content = sectionMessage + classroomUsersTable;
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = content;
			document.getElementById("classroomId").value = "";
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retreive classroom members info from the server";
		}
	}
    xmlhttp.send();
};

function loadMediaItems(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const urlPath = baseUrl + "/mediaitems";
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No media items found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}
			
		    const mediaItems = JSON.parse(xmlhttp.responseText);
		    
			const mediaItemsTable = getMediaItems(mediaItems);
			
			const sectionMessage = '<div class="section-message">Media Items Info</div>';
			
			const content = sectionMessage + mediaItemsTable;
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retrieve media items info from the server";
		}
	}
    xmlhttp.send();
};

function loadMediaItemsPerType(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const mediaItemTypeInput = document.getElementById("itemType").value;
	const urlPath = baseUrl + "/mediaitemspertype/" + mediaItemTypeInput;
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No item type selected!") || 
					(xmlhttp.responseText === "Invalid item type!") ||
					(xmlhttp.responseText === "Found No items of this type!") ||
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
				document.getElementById("itemType").value = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}
		    const mediaItems = JSON.parse(xmlhttp.responseText);
		    
			const mediaItemsPerTypeTable = getMediaItems(mediaItems);
			
			const sectionMessage = '<div class="section-message">Media Items Info For Type ' + mediaItemTypeInput + '</div>';
			
			const content = sectionMessage + mediaItemsPerTypeTable;
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		    document.getElementById("itemType").value = "";
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retrieve media items per selected type info from the server";
		}
	}
    xmlhttp.send();
};

function loadMediaItemsCurrentlyBooked(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const urlPath = baseUrl + "/bookedmediaitems";
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No booked items found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}

		    const mediaItemsBooked = JSON.parse(xmlhttp.responseText);
		    
			const mediaItemsBookedTable = getMediaItems(mediaItemsBooked);
			
			const sectionMessage = '<div class="section-message">Media Items Currently Booked Info</div>';
			
			const content = sectionMessage + mediaItemsBookedTable;
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retrieve media items currently booked info from the server";
		}
	}
    xmlhttp.send();
};

function loadMediaItemsPreviouslyBooked(){
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const urlPath = baseUrl + "/previouslybookeditems";
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No booked items found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}

		    const mediaItemsBooked = JSON.parse(xmlhttp.responseText);
		    
			const mediaItemsBookedTable = getMediaItems(mediaItemsBooked);
			
			const sectionMessage = '<div class="section-message">Media Items Previously Booked Info</div>';
			
			const content = sectionMessage + mediaItemsBookedTable;
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't retrieve media items previously booked info from the server";
		}
	}
    xmlhttp.send();
};

function createAddMediaItemForm() {
	const formContent = '<div class="section-message">' +
			'Please fill in the following information to add a new media item'+
			'</div>' +
			'<form id="addMediaItem" name="addMediaItem" onsubmit="sendRequest()">' +
			'<label class="a-label" for="itemType">Media Item Type:' +
				'<input class="an-input" type="text" id="itemType" name="itemType">' +
			'(accepted values: book, audio-book, dvd)</label>' +
			'<label class="a-label" for="itemTitle">Media Item Title:' +
				'<input class="an-input" type="text" id="itemTitle" name="itemTitle">' +
				'</label>' +
			'<label class="a-label" for="itemAuthorFirstName">Media Item Author First Name:' +
				'<input class="an-input" type="text" id="itemAuthorFirstName" name="itemAuthorFirstName">' +
				'</label>' +
			'<label class="a-label" for="itemAuthorLastName">Media Item Author Last Name:' +
				'<input class="an-input" type="text" id="itemAuthorLastName" name="itemAuthorLastName">' +
			'</label>' +
			'<label class="a-label "for="itemReleasedYear">Media Item Released Year:' +
				'<input class="an-input" type="text" id="itemReleasedYear" name="itemReleasedYear">' +
			'</label>' +
			'<input class="submit-button" type="submit" id="submitMediaItem" name="submitMediaItem" value="Add Media Item">' +
			'</form>';
	document.getElementById("mediaLibraryStore").innerHTML = "";
    document.getElementById("mediaLibraryStore").innerHTML = formContent;
};

function sendRequest() {
	event.preventDefault();
	const form = document.getElementById("addMediaItem");
	const formData = new FormData(form);
	
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const urlPath = baseUrl + "/addmediaitem";
	
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No booked items found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}

		    const sectionMessage = '<div class="section-message">Submission response received: \n';
			const content = sectionMessage + xmlhttp.responseText + '</div>';
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't add your item to the store inventory. Please try again.";
		}
	}
	console.log(formData);
    xmlhttp.send(formData);
    return false;
};

function createAddUserForm() {
	const formContent = '<div class="section-message">' +
			'Please fill in the following information to add a new user:'+
			'</div>' +
			'<form id="addUser" name="addUser" onsubmit="sendRequestUser()">' +
			'<label class="a-label" for="userFirstName">User First Name:' +
				'<input class="an-input" type="text" id="userFirstName" name="userFirstName">' +
				'</label>' +
			'<label class="a-label" for="userLastName">User Last Name:' +
				'<input class="an-input" type="text" id="userLastName" name="userLastName">' +
			'</label>' +
			'<label class="a-label" for="userUsername">User Username:' +
			'<input class="an-input" type="text" id="userUsername" name="userUsername">' +
			'</label>' +
			'<label class="a-label "for="userPassword">User password:' +
				'<input class="an-input" type="password" id="userPassword" name="userPassword">' +
			'</label>' +
			'<label class="a-label" for="userGroup">User Group Type:' +
			'<input class="an-input" type="text" id="userGroup" name="userGroup">' +
			'(accepted values: student, teacher, manager)</label>' +
			'<input class="submit-button" type="submit" id="submitUser" name="submitUser" value="Add User">' +
			'</form>';
	document.getElementById("mediaLibraryStore").innerHTML = "";
    document.getElementById("mediaLibraryStore").innerHTML = formContent;
};

function sendRequestUser() {
	event.preventDefault();
	const form = document.getElementById("addUser");
	const formData = new FormData(form);
	
	const baseUrl = "http://localhost:8080/MediaLibraryStore";
	const urlPath = baseUrl + "/adduser";
	
	
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", urlPath, true);
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
			if ((xmlhttp.responseText === "No booked items found!") || 
					(xmlhttp.responseText.includes("Internal error!"))) {
				document.getElementById("mediaLibraryStore").innerHTML = "";
			    document.getElementById("mediaLibraryStore").innerHTML = '<div class="section-message">' + xmlhttp.responseText + '</div>';
			    return;
			}

		    const sectionMessage = '<div class="section-message">Submission response received: \n';
			const content = sectionMessage + xmlhttp.responseText + '</div>';
			
			document.getElementById("mediaLibraryStore").innerHTML = "";
		    document.getElementById("mediaLibraryStore").innerHTML = content;
		} else {
			document.getElementById("mediaLibraryStore").innerHTML = "";
			document.getElementById("mediaLibraryStore").innerHTML = "We couldn't add the new user. Please try again.";
		}
	}
	console.log(formData);
    xmlhttp.send(formData);
    return false;
};