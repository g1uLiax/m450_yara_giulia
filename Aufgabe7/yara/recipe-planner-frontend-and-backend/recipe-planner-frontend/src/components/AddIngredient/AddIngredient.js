import React from 'react';
import PropTypes from 'prop-types';
import './AddIngredient.css';
import {Button, Col, Form, Row} from "react-bootstrap";

const AddIngredient = ({ingredient, updateIngredient, removeIngredient}) => {

    const handleChange = (e) => {
        const { name, value } = e.target;
        updateIngredient({
            ...ingredient,
            [name]: name === 'amount' ? parseInt(value) || 0 : value
        });
    };

    return (
        <Row className="align-items-center mb-2">
            <Col>
                <Form.Group controlId={`formName-${ingredient.listId}`}>
                    <Form.Control 
                        name="name" 
                        value={ingredient.name || ''} 
                        onChange={handleChange} 
                        placeholder="Ingredient Name"
                    />
                </Form.Group>
            </Col>
            <Col>
                <Form.Group controlId={`formUnit-${ingredient.listId}`}>
                    <Form.Select 
                        name="unit" 
                        value={ingredient.unit || 'PIECE'} 
                        onChange={handleChange}
                    >
                        <option value="PIECE">PIECE</option>
                        <option value="GRAMM">GRAMM</option>
                        <option value="KILOGRAMM">KILOGRAMM</option>
                        <option value="LITRE">LITRE</option>
                        <option value="DECILITRE">DECILITRE</option>
                    </Form.Select>
                </Form.Group>
            </Col>
            <Col>
                <Form.Group controlId={`formAmount-${ingredient.listId}`}>
                    <Form.Control 
                        name="amount" 
                        type="number"
                        value={ingredient.amount || ''} 
                        onChange={handleChange} 
                        placeholder="Amount"
                    />
                </Form.Group>
            </Col>
            <Col xs={1}>
                <Button
                    onClick={() => removeIngredient(ingredient)}
                    variant='outline-danger'
                    className="w-100"
                >✕</Button>
            </Col>
        </Row>
    )
}

AddIngredient.propTypes = {};

AddIngredient.defaultProps = {};

export default AddIngredient;
