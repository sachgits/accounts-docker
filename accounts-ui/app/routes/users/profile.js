import Ember from 'ember';
import AuthenticatedRouteMixin from 'ember-simple-auth/mixins/authenticated-route-mixin';
 

export default Ember.Route.extend(AuthenticatedRouteMixin, {

    notifications: Ember.inject.service('notification-messages'),

    model(params) {  
        return this.store.findRecord('user', params.id );
    },

 	beforeModel () {  
        console.log("beforeModel"); 
        
        this.store.adapterFor('application').set('namespace', "user/api/v01/secure");  
    },
 
  
    validation: Ember.inject.service(),  
 	actions: {
		editUser(user) {
			console.log("editUser: " + user);
			user.set('isEditing', true); 
            this.controller.set('disableUpdateButton', false);
	 	},

	 	updateUser(user) {  
	 		console.log("update user : " + user.id); 
            user.validate({ on: ['first_name', 'last_name', 'purpose' ] }) 
                .then(({  validations }) => {
                    console.log("is valid !" + validations.get('isValid'));
                    if (validations.get('isValid')) { 
                        console.log("valid"); 
                        user.save()
                            .then((record) => {   
                                console.log("record : " + record);
                                this.set('showSaved', true);   
                            }).catch((msg) => {
                                 console.log("error : " + msg.toString());
                                 if(msg.toString() === 'Error: The adapter operation was aborted') { 
                                    this.controller.get('model').rollbackAttributes();
                                 }
                            }).finally((response) => {  
                                console.log("finally response : " + response);
                            });
                    } else {
                        console.log('invalid');  
                        user.set('isEditing', true); 
                     //   this.controller.get('model').rollbackAttributes();
                      // this.controller.get('model').rollbackAttributes();
                    } 
                });  
	 		user.set('isEditing', false);
	 	},

        changePassword(user) {  
        	console.log("changePassword: " + user);
            user.set('changePassword', true);
        },

        cancelUpdatePassword(user) {
        	console.log("cancelEditUser: " + user);
        	user.set('changePassword', false);
        },


        cancelEditUser(user) {
            console.log("cancelEditUser: " + user);
            this.controller.get('model').rollbackAttributes();
            user.set('isEditing', false);
        },

  
        updatePassword(user) {
        	console.log("uppdatePassword: " + user.password);

            let controller = this.controller; 
            user.validate({ on: ['password', 'passwordConfirmation', 'old_password' ] }) 
                .then(({ validations }) => {
                    if (validations.get('isValid')) {  
                        user.save()
                            .then((record) => {   
                                console.log("record : " + record);
                                this.set('showSaved', true);  
                                controller.set('updatePasswordResponseMessage', true);


                                user.set('changePassword', false);
                                user.set('password', null);
                                user.set('passwordConfirmation', null);
                                user.set('old_password', null);

                                this.get('notifications').success(this.get('i18n').t('messages.password-update-success'), {
                                  autoClear: false,
                                }); 
                   
                            }).finally(()=>{
                                controller.set('isSaving', false);
                            });
                    } else {
                        console.log('invalid');  
                        user.set('changePassword', true);
                    } 
                });       
        },

        willTransition() {
            const unsavedModel = this.get('unsaved');
            if (unsavedModel) {
              this.refresh();
            }
        }
 	}

});
