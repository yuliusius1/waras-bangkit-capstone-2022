const mysql = require("mysql")

const pool = mysql.createPool({
	user: process.env.DB_USER,
	password: process.env.DB_PASS,
	database: process.env.DB_NAME,
	socketPath: process.env.INSTANCE_CONNECTION_NAME,
});

module.exports = pool;