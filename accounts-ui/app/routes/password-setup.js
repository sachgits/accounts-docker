import Ember from 'ember';

export default Ember.Route.extend({
    model(params) {  
        console.log("id: " + params.id);
        return this.store.findRecord('user', params.id );
    },


    beforeModel () {   
        this.store.adapterFor('application').set('namespace', "user/api/v01"); 
    },

    actions: { 
        submit (user) {  
            console.log("uppdatePassword: " + user.password);

            let controller = this.controller; 
            user.validate({ on: ['password', 'passwordConfirmation' ] }) 
                .then(({ validations }) => {
                    if (validations.get('isValid')) {  
                        user.save()
                            .then((record) => {   
                                console.log("record : " + record);
                            //    this.set('showSaved', true);  
                            //    controller.set('updatePasswordResponseMessage', true);
 
                                user.set('password', null);
                                user.set('passwordConfirmation', null); 

                            //    this.get('notifications').success(this.get('i18n').t('messages.password-update-success'), {
                            //      autoClear: false,
                            //    }); 
                            }).catch((msg) => {
                                 console.log("error : " + msg.toString());
                                 if(msg.toString() === 'Error: The adapter operation was aborted') { 
                                    controller.get('model').rollbackAttributes();
                                 }
                   
                            }).finally(()=>{
                            //    controller.set('isSaving', false);
                                this.transitionTo('login');   
                            });
                    } else {
                        console.log('invalid');   
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
