const jwt = require("jsonwebtoken");

module.exports = (req, res, next) => {
    const token = req.header("x-auth-token");
    if (!token){
        res.status(401);
        return res.json({status: "Error", message : "Access denied, No token provided"});
    } 
    try {
        const decoded = jwt.verify(token, "jwtPrivateKey");
        req.users = decoded;
    } catch {
        res.status(401);
        return res.json({status: "Error", message: "Token Expire!"});
    }

    next();
}
