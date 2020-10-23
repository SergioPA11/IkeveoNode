module.exports = app => {
    const gafas = require("../controllers/ikeveo.controller.js");
  
    var router = require("express").Router();
  
    // Create a new Bicycle
    router.post("/", gafas.create);
  
    // Retrieve all gafas
    router.get("/", gafas.findAll);
  
    // Retrieve a single bicycle with id
    router.get("/:id", gafas.findOne);
  
    // Update a bicycle with id
    router.put("/:id", gafas.update);
  
    // Delete a bicycle with id
    router.delete("/:id", gafas.delete);
  
    app.use('/api/gafas', router);
  };