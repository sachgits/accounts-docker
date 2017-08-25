import Ember from 'ember';

import AuthenticatedRouteMixin from 'ember-simple-auth/mixins/authenticated-route-mixin';

export default Ember.Route.extend(AuthenticatedRouteMixin, { 
 
    beforeModel () {   
        console.log("beforeModel");
        this.store.adapterFor('application').set('namespace', "user/api/v01/secure");   
        this.transitionTo('index');
    } 
});
