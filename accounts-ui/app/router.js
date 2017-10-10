import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL 
});

Router.map(function() {
  this.route('login');
  this.route('register');
  this.route('password-recover');
  this.route('email-verification');
  this.route('email-expired');

  this.route('admin', function() {
    this.route('users', function() {
      this.route('new');
      this.route('view', {path: '/:id'});
 //     this.route('edit', {path: '/:id/edit'});
    });
  });

  this.route('users', function() {
    this.route('profile', {path: '/:id'}); 
  //  this.route('setup-password', {path: 'setup-password/:id'}); 
  });


  this.route('clients', function() {
    this.route('new');
    this.route('view', {path: ':id'});
  });

 // this.route('users.account', {
 //    path: 'users/account',
// });

  this.route('password-setup', { path: '/password-setup/:id' });
   
 
});

export default Router;
