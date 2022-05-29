const jwt = require("jsonwebtoken");
const express = require("express");
const bcrypt = require("bcrypt");
const pool = require("../sql");

const router = express.Router();



router.post("/", async (req, res) => {
	const username = req.query.username
	const password = req.query.password
	if (username == null || username == '' || password == ''|| password == null){
        	res.status(400);
		return res.json({ status: "Error", message: "Please input username and/or password!"});
	} 
	const query1 = `SELECT * FROM tbauth WHERE username = '${username}'`;
	try {
		pool.query(query1, async(error, result)=>{
			const users = result[0];
			if (!result[0]) {  
				res.status(404);
				return res.json({status: "Error", message: "User Not found!"});
			}
			if(await bcrypt.compare(password , result[0].password)) {
				const token = jwt.sign({
					id: result[0]._id,
					roles: result[0].roles,
				}, "jwtPrivateKey", { expiresIn: "14d" });

				res.json({status: "Success", token : token});
			} else {
                    		res.status(401);
				res.json({status: "Error", message: "Incorrect Username and/or Password!"});
			}
			});
	} catch {
            	res.status(500);
		res.json({status: "Error"});
	}
});

module.exports = router;
