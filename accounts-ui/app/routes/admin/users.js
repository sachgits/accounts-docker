import Ember from 'ember';
import AuthenticatedRouteMixin from 'ember-simple-auth/mixins/authenticated-route-mixin';

export default Ember.Route.extend(AuthenticatedRouteMixin,{
  
    session: Ember.inject.service(),  

    queryParams:{
        status: {
            refreshModel: true,
            scope: 'controller',
        }
    },
 //   status: null,
    isList: true,
    isDetail: false,

    model(params) {
        console.log("params : " + params.status);  
        if(params.status === null) {
            return this.store.findAll('user');
        }
        return  this.store.query('user', { filter: { status: params.status,
                                                     action: 'filterStatus' }  });
   //     return  this.store.query('user', { filter: { status: params.status}, reload: true });
    },
 
    activate () { 
        console.log("activate users : " + status);
        this.controllerFor('admin.users').set('isList', true);   
        this.controllerFor('admin.users').set('isDetail', false);   
 //       this.controllerFor('admin.users').set('isLoogedIn', false);   
    },

    beforeModel () {   
        console.log("beforeModel"); 
        this.store.adapterFor('application').set('namespace', "user/api/v01/secure");    
        const isAdmin = this.get('session.user_profile.isAdmin'); 
        if(!isAdmin) {
            this.transitionTo('index');
        }
    },   
});
