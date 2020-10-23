module.exports = (sequelize, Sequelize) => {
    const Gafas = sequelize.define("gafas", {
      brand: {
        type: Sequelize.STRING
      },
      model: {
        type: Sequelize.STRING
      }
    }, { timestamps: false});
  
    return Gafas;
  };