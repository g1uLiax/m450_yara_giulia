describe('Create Student', () => {

  it('should create a new student and redirect to list', () => {

    cy.intercept('POST', 'http://localhost:8081/students', {
      id: '99',
      name: 'John Tester',
      email: 'john@test.ch'
    }).as('saveStudent');

    cy.intercept('GET', 'http://localhost:8081/students', [
      { id: '99', name: 'John Tester', email: 'john@test.ch' }
    ]).as('reloadStudents');

    cy.visit('/addstudents');

    cy.get('#name').type('John Tester');
    cy.get('#email').type('john@test.ch');
    cy.get('button[type=submit]').click();

    cy.wait('@saveStudent');
    cy.wait('@reloadStudents');

    cy.url().should('include', '/students');
    cy.contains('John Tester').should('exist');
  });

});
