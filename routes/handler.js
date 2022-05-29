const express = require("express")
const bcrypt = require("bcrypt")
const auth = require("../middleware/auth");
const { admin, user } = require("../middleware/roles");
const pool = require("../sql");

const router = express.Router();

//GET USER ALL DATA
router.get("/users", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbuserwaras";
 	pool.query(query, (error, result)=>{
		res.json(result); 
	});
});

//GET DIAGNOSE ALL DATA
router.get("/diagnoses", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbdiagnose";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "Diagnose get!", data: result });
	});
});

//GET recommendation ALL DATA
router.get("/recommendations", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbrecom";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "recommendations get!", data: result });
	});
});

//GET ALL datarecommendation 
router.get("/datarecommendations", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbdatarecom";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "datarecommendations get!", data: result });
	});
});

//GET ALL HISTORY DATA 
router.get("/history", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbhistory";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "History Get", data: result }); 
	});
});

//GET USER DATA BY USERNAME
router.get("/users/username", [auth, admin], async (req, res)=>{
	const username = req.query.username
	const query = `SELECT * FROM tbuserwaras WHERE username = '${username}'`;
	pool.query(query, [req.params.username], (error, result)=>{
		if (!result[0]) {
            res.status(404);
			res.json({status: "Error", message: "User Not found!"});
		} else {
			res.json({status: "Success", message : "User Get!", data: result[0]});
		} 
	});
});

//GET DIAGNOSE DATA BY ID
router.get("/diagnoses/:id", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbdiagnose WHERE id_diagnose= ?";
	pool.query(query, [req.params.id], (error, result)=>{
		if (!result[0]) {
            res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "Diagnose Get!", data: result[0]});
		} 
	});
});

//GET recommendation DATA BY ID
router.get("/recommendations/:id", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbrecom WHERE id_recommendations = ?";
	pool.query(query, [req.params.id], (error, result)=>{
		if (!result[0]) {
            res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "recommendations Get!", data: result[0]});
		} 
	});
});

//GET datarecommendation BY ID
router.get("/datarecommendations/:id", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbdatarecom WHERE id_dataRecom = ?";
	pool.query(query, [req.params.id], (error, result)=>{
		if (!result[0]) {
            res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "datarecommendations Get!", data: result[0]});
		} 
	});
});

//GET HISTORY DATA BY ID
router.get("/history/:id", [auth, admin], async (req, res)=>{
	const query = "SELECT * FROM tbhistory WHERE id_history = ?";
	pool.query(query, [req.params.id], (error, result)=>{
		if (!result[0]) {
            res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "History Get!", data: result[0]});
		} 
	});
});

//POST CREATE ADMIN DATA TO DB
router.post("/token", [auth, admin], async (req, res)=>{
	try {
		const hashedPassword = await bcrypt.hash(req.query.password, 10)
		const created_at = new Date().toISOString();
		const data = {
			username: req.query.username,
			password: hashedPassword,
			roles: req.query.roles,
			created_at: created_at,
		}
		const query1 = "INSERT INTO tbauth (username, password, roles, created_at) VALUES (?, ?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    res.status(409);
					res.json({status: "Error", message : "Username and/or Email already exists!" });
				} else {
                    res.status(201);
					res.json({status: "Success", message : "User Created!" });
				}
		});
  } catch(error) {
        res.status(500);
	 	res.json({ status: "Error"});  
    }
});

//POST REGISTER DATA TO DB
router.post("/register", [auth, admin], async (req, res)=>{
	try {
		const hashedPassword = await bcrypt.hash(req.query.password, 10)
		const created_at = new Date().toISOString();
  	const updatedAt = created_at;
		const data = {
			username: req.query.username,
			gender: req.query.gender,
			full_name: req.query.full_name,
			email: req.query.email,
			password: hashedPassword,
			telephone: req.query.telephone,
			date_of_birth: req.query.date_of_birth,
			created_at: created_at,
			updated_at: updatedAt,
		}
		const query1 = "INSERT INTO tbuserwaras ( username, gender, full_name,  email, password, telephone, date_of_birth, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    res.status(409);
					res.json({status: "Error", message : "Username and/or Email already exists!" });
				} else {
                    res.status(201);
					res.json({status: "Success", message : "User Created!" });
				}
		});
  } catch {
        res.status(500);
		res.json({ status: "Error"});  
    }
});

//VALIDATE LOGIN
router.post("/login", [auth, admin], async (req, res) => {
    const username = req.query.username
    const password = req.query.password
    if (username == null || username == '' || password == ''|| password == null){
        res.status(400);
        return res.json({ status: "Error", message: "Please input username and/or password!"});
    } 
        const query1 = `SELECT * FROM tbuserwaras WHERE username = '${username}'`;
    try {
            pool.query(query1, async(error, result)=>{
                if (!result[0]) {  
                    res.status(404);
                    return res.json({status: "Error", message: "User Not found!"});
                }
                if(await bcrypt.compare(password , result[0].password)) {
                    res.json({status: "Success", data : result[0]});
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

//VALIDATE PASSWORD 
router.post("/users/changePassword", [auth, admin], async (req, res) => {
	const username = req.query.username
	const password = req.query.password
	if (username == null || username == '' || password == ''|| password == null){
        res.status(400);
		return res.json({status: "Error", message: "Please input username and/or password"});
	} 
	const query1 = `SELECT * FROM tbuserwaras WHERE username = '${username}'`;
	try {
			pool.query(query1, async(error, result)=>{
				if (!result[0]) {  
                    res.status(404);
					return res.json({status: "Error", message: "User Not found!"});
				}
				if(await bcrypt.compare(password , result[0].password)) {
					res.json({ status: "Success"});
				} else {
                    res.status(401);
					res.json({ status: "Error", message : "Incorrect Password!"});
				}
			});
		} catch {
            res.status(500);
			res.json({status: "Error"});
		}
});

//VALIDATE EMAIL
router.post("/email", [auth, admin], async (req, res) => {
	const email = req.query.email
	if (email == null || email == ''){
        res.status(400);
		return res.json({ status: "Error", message: "Please input email!"});
	} 
	const query1 = `SELECT * FROM tbuserwaras WHERE email = '${email}'`;
	try {
			pool.query(query1, async(error, result)=>{
				if (!result[0]) {  
                    res.status(404);
					return res.json({status: "Error", message: "Email Not found!"});
				} else {
					res.json({ status: "Success", data : result[0]});
				}
			});
		} catch {
            res.status(500);
			res.json({status: "Error"});
		}
});

//POST DIAGNOSE DATA TO DB
router.post("/diagnoses", [auth, admin], async (req, res)=>{
	try {
		const created_at = new Date().toISOString();
		const data = {
			age: req.query.age,
			gender: req.query.gender,
			fever: req.query.fever,
			cough: req.query.cough,
			tired: req.query.tired,
			sore_throat: req.query.sore_throat,
			runny_nose: req.query.runny_nose,
			short_breath: req.query.short_breath,
			vomit: req.query.vomit,
			day_to_heal: req.query.day_to_heal,
			created_at: created_at,
			id_user: req.query.id_user,
		}
		const query1 = "INSERT INTO tbdiagnose (age, gender, fever, cough, tired, sore_throat, runny_nose, short_breath, vomit, day_to_heal, created_at, id_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    res.status(201);
					res.json({status: "Success", message : "Diagnose Created!", data:data});
				}
		});
  } catch {
		res.status(500);
		res.json({ status: "Error"});  
    }
});

//POST recommendationS DATA TO DB
router.post("/recommendations", [auth, admin], async (req, res)=>{
	try {
		const created_at = new Date().toISOString();
		const data = {
			recommendations: req.query.recommendations,
			created_at: created_at,
			id_user: req.query.id_user,
		}
		const query1 = "INSERT INTO tbrecom (recommendations, created_at, id_user) VALUES (?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "recommendations Created!" });
				}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error" });  
    }
});

//POST datarecommendation TO DB
router.post("/datarecommendations",[auth, admin],  async (req, res)=>{
	try {
		const data = {
		data_recommendations: req.query.data_recommendations,
		}
		const query1 = "INSERT INTO tbdatarecom (data_recommendations) VALUES (?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "datarecommendations Created!" });
				}
		});
  } catch {
        	res.status(500);
		res.json({ status: "Error"});  
    }
});

//POST HISTORY DATA TO DB
router.post("/history", [auth, admin], async (req, res)=>{
	try {
		const created_at = new Date().toISOString();
		const data = {
			day_to_heal: req.query.day_to_heal,
			date_to_heal: req.query.date_to_heal,
			status: req.query.status,
			recommendations: req.query.recommendations,
			created_at: created_at,
			id_user: req.query.id_user,
            		id_diagnose: req.query.id_diagnose,
		}
		const query1 = "INSERT INTO tbhistory (day_to_heal, date_to_heal, status, recommendations, created_at, id_user, id_diagnose) VALUES (?,?, ?, ?, ?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
			if (error){
				 res.status(400);
				res.json({status: "Error", message : "Please fill correctly!"});
			} else {
				res.status(201);
				res.json({status: "Success", message : "History Created!" });
			}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error"});  
    }
});

//UPDATE PASSWORD TO DB
router.put("/users/changePassword", [auth, admin], async (req, res)=>{
	const username = req.query.username
	if (username == null || username == '' || req.query.password == ''|| req.query.password == null){
    		res.status(400);
		return res.json({status: "Error", message: "Please input username and/or password"});
	}
	const hashedPassword = await bcrypt.hash(req.query.password, 10)
	let password = hashedPassword;
  	let updated_at = new Date().toISOString();
	const data = {
		password: password,
		updated_at: updated_at,
	}
	const query1 = `UPDATE tbuserwaras SET password ='${password}', updated_at='${updated_at}' WHERE username = '${username}'`;
	const query2 = `SELECT * FROM tbuserwaras WHERE username = '${username}'`;
	try {
		pool.query(query2, (error, result)=>{
		if (!result[0]) {  
			res.status(404);
			return res.json({status: "Error", message: "User Not found!"});
		}	
		pool.query(query1, (error, result)=>{
		res.json({status: "Succes", message: "Update Succes!", data : data});		
		})	
		});
	} catch {
		res.json({status: "Error", reason: 500});
	}
});

//UPDATE ALL DATA TO DB
router.put("/users/:id", [auth, admin], async (req, res)=>{
	let id = req.params.id;
	let username = req.query.username;
	let gender = req.query.gender;
	let full_name = req.query.full_name;
	let email = req.query.email;
	let telephone = req.query.telephone;
	let date_of_birth = req.query.date_of_birth;
  	let updated_at = new Date().toISOString();
	const data = {
		username: username,
		gender: gender,
		full_name: full_name,
		email: email,
		telephone: telephone,
		date_of_birth: date_of_birth,
	}
	const query1 = `UPDATE tbuserwaras SET username = '${username}', gender = '${gender}',full_name ='${full_name}', email ='${email}', telephone= '${telephone}', date_of_birth = '${date_of_birth}', updated_at='${updated_at}' WHERE id = ${id}`;
	const query2 = `SELECT * FROM tbuserwaras WHERE id= ${id}`;
	try {
		pool.query(query2, (error, result)=>{
			if (!result.length) {  
        			res.status(404);
				return res.json({status: "Error", message: "ID Not found!"});
			}
			pool.query(query1, (error, result)=>{
			if(error){
        			res.status(409);
				return res.json({status: "Error", message: "Username, Full name or Email already exists!"});
			} else {
				res.status(201);
				res.json({status: "Succes", message: "Update Succes!", data: data});
			}
		})	
		});
	} catch {
    		res.status(500);
		res.json({status: "Error"});
	}
});


//DELETE USER DATA FROM DB
router.delete("/users/:id", [auth, admin], async (req, res)=>{
	const id = req.params.id
	const query1 = `SELECT * FROM tbuserwaras WHERE id= ${id}`;
	const query = `DELETE FROM tbuserwaras WHERE id= ${id}`;
		try {
			pool.query(query1, (error, result)=>{
				if (!result.length) {  
                    res.status(404);
					return res.json({status: "Error", message: "User Not found!"});
				} else {
					pool.query(query, (error, result)=>{
						res.json({status: "Succes!"});
					})
				}
			});
		} catch {
            		res.status(500);
			res.json({status: "Error"});
		}
});

module.exports = router;
