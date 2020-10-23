const db = require("../models");
const Gafas = db.gafas;
const Op = db.Sequelize.Op;

// Create and Save a new Gafas
// req --> request (contains the body)
exports.create = (req, res) => {
  // Validate request
  if (!req.body.brand || !req.body.model) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  // Create a Gafas
  const gafas = {
    brand: req.body.brand,
    model: req.body.model
  };

  // Save Gafas in the database
  Gafas.create(gafas)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the Gafas."
      });
    });
};

// Retrieve all Gafas from the database.
exports.findAll = (req, res) => {
  
    Gafas.findAll()
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving Gafas."
        });
      });
};

// Find a single Gafas with an id
exports.findOne = (req, res) => {
  let id = req.params.id;
  Gafas.findByPk(id)
    .then(data => {
      console.log("estos son los datos")
      console.log(data);
      if(!data){
        res.status(400).send({
          message:
            "No Gafas found with that id"
        })
      }
      res.send(data);
      return;
    })
    .catch(err => {
      console.log(err.message);
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving Gafas with id"
      });
      return;
    });
};

// Update a Gafas by the id in the request
exports.update = (req, res) => {
  const id = req.params.id;

  Gafas.update(req.body, {
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Gafas was updated successfully."
        });
      } else {
        res.send({
          message: `Cannot update Gafas with id=${id}. Maybe Gafas was not found or req.body is empty!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Error updating Gafas with id=" + id
      });
    });
};

// Delete a Tutorial with the specified id in the request
exports.delete = (req, res) => {
  const id = req.params.id;

  Gafas.destroy({
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Gafas was deleted successfully!"
        });
      } else {
        res.send({
          message: `Cannot delete Gafas with id=${id}. Maybe Gafas was not found!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete Gafas with id=" + id
      });
    });
};
