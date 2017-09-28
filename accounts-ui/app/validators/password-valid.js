import Ember from 'ember';
import BaseValidator from 'ember-cp-validations/validators/base';

const PasswordValid = BaseValidator.extend({
  store: Ember.inject.service(),

  validate(value, model, attribute) { 
    console.log("attribute : " + attribute.get('email'));
    console.log("value : " + value);
    return this.get('store').query('user', { 
      filter: { email: attribute.get('email'),
                password : value,
                action: 'validatePassword' }
    })
    .then((result) => {  
      return Ember.isEmpty(result) ? `Incorrect password` : true; 
  //   if(result.get('length') === 0) {
  //     return true;
   //   } else {
  //      return "The username is already in use";
  //    }
    });
  } 
});

PasswordValid.reopenClass({
  /**
   * Define attribute specific dependent keys for your validator
   *
   * [
   * 	`model.array.@each.${attribute}` --> Dependent is created on the model's context
   * 	`${attribute}.isValid` --> Dependent is created on the `model.validations.attrs` context
   * ]
   *
   * @param {String}  attribute   The attribute being evaluated
   * @param {Unknown} options     Options passed into your validator
   * @return {Array}
   */
  getDependentsFor(/* attribute, options */) {
    return [];
  }
});

export default PasswordValid;
