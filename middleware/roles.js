function admin(req, res, next) {
	if (!req.users.roles.includes("admin")) {
        res.status(400);
		return res.json({status: "Error", message : "Access denied"});
	}
	next();
}

function user(req, res, next) {
	if (!req.users.roles.includes("user")){
        res.status(400);
		return res.json({status: "Error", message : "Access denied"});
	}
	next();
}

module.exports = { admin, user };
