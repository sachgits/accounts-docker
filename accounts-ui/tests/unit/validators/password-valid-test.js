import { moduleFor, test } from 'ember-qunit';

moduleFor('validator:password-valid', 'Unit | Validator | password-valid', {
  needs: ['validator:messages']
});

test('it works', function(assert) {
  var validator = this.subject();
  assert.ok(validator);
});
