function admin(req, res, next) {
	if (!req.users.roles.includes("admin")) {
		return res.json({status: "Error", message : "Access denied"});
	}
	next();
}

function user(req, res, next) {
	if (!req.users.roles.includes("user")){
		return res.json({status: "Error", message : "Access denied"});
	}
	next();
}

module.exports = { admin, user };