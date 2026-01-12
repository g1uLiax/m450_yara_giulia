describe('Student Form Validation', () => {

  it('Validates required fields and enables submit when form is valid', () => {
    cy.visit('/addstudents');

    cy.get('button[type=submit]').should('be.disabled');

    cy.get('#name').focus().blur(); // simulates field clicked and then being unclicked
    cy.contains('Name is required').should('exist');

    cy.get('#email').focus().blur();
    cy.contains('Email is required').should('exist');

    cy.get('#name').type('Anna');
    cy.get('#email').type('anna@test.ch');

    cy.get('button[type=submit]').should('not.be.disabled');
  });

});
