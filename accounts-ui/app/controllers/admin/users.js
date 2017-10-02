import Ember from 'ember';

export default Ember.Controller.extend({

    statusFilterOptions: [  
        { status: 'Enabled' }, 
        { status: 'Disabled' },
        { status: 'Pending' }
    ],

    queryParams:{
        status: {
            refreshModel:true
        }
    },
    status: null, 
    isLoggedIn: false,


    filteredUsers: Ember.computed('status', 'model', function() {
        var status = this.get('status');
        var users = this.get('model');

        if (status) {
          return users.filterBy('status', status);
        } else {
          return users;
        }
    }),

    actions: {  
        loggedInUser() {
            console.log("loggedInUser " + this.get('status')); 
 
            let isLoggedIn = false;
            if( this.toggleProperty('isLoggedIn')) {  
                this.set('status',  "loggedIn");     
                status = null;
                this.set('selectedFilter', null); 
            } else { 
                this.set('status', null);    
            }
        },

        statusChange(filter) {
            console.log("statusChange : "  + filter);
            

            let status = null;
            this.set('selectedFilter', filter); 
 
            if(filter !== null) {
                status = filter.status;  
            } 
  
            this.set('status', status);   

            if(this.get('isLoggedIn')) {
                this.toggleProperty('isLoggedIn');
            }

          //  let controller = this.controllerFor('user.status');
          //  controller.set("status", status);
         //   this.send("statusChange");
          //  this.transitionToRoute('users.status');
        },  
    }
});
