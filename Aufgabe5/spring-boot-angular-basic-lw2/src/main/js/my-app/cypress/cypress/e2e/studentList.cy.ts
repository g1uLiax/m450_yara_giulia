describe('Student List', () => {

  it('Should display a list of students', () => {
    cy.intercept('GET', 'http://localhost:8081/students', [
      { id: '1', name: 'Max Muster', email: 'max@test.ch' },
      { id: '2', name: 'Lisa Beispiel', email: 'lisa@test.ch' }
    ]).as('getStudents');

    cy.visit('/students');
    cy.wait('@getStudents');

    cy.contains('Max Muster').should('exist');
    cy.contains('lisa@test.ch').should('exist');
  });

});
