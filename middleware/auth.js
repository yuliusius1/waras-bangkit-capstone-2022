const jwt = require("jsonwebtoken");

module.exports = (req, res, next) => {
    const token = req.header("x-auth-token");
    if (!token){
     return res.json({status: "Error", message : "Access denied, No token provided"});
    } 
    try {
        const decoded = jwt.verify(token, "jwtPrivateKey");
        req.users = decoded;
    } catch {
        return res.json({status: "Error"});
    }

    next();
}