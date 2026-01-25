import React from 'react';
import PropTypes from 'prop-types';
import './AddIngredient.css';
import {Button, Col, Form, Row} from "react-bootstrap";

const AddIngredient = ({ingredients, ingredient, updateIngredient, removeIngredient}) => {

    const handleChange = (e) => {
        const { name, value } = e.target;
        updateIngredient({ ...ingredient, [name]: value });
    }

    return (
        <Row>
            <Col>
                <Form.Group className="mb-1" controlId="ingredient">
                    <Form.Control name="ingredient" placeholder="Name" value={ingredient.ingredient} onChange={handleChange}/>
                </Form.Group>
            </Col>
            <Col>
                <Form.Group className="mb-1" controlId="unit">
                    <Form.Select name="unit" value={ingredient.unit} onChange={handleChange}>
                        <option value="PIECE">PIECE</option>
                        <option value="GRAMM">GRAMM</option>
                        <option value="KILOGRAMM">KILOGRAMM</option>
                        <option value="LITRE">LITRE</option>
                        <option value="DECILITRE">DECILITRE</option>
                    </Form.Select>
                </Form.Group>
            </Col>
            <Col>
                <Form.Group className="mb-1" controlId="quantity">
                    <Form.Control name="quantity" placeholder="Quantity" value={ingredient.quantity} onChange={handleChange}/>
                </Form.Group>
            </Col>
            <Col xs={1}>
                <Button
                    onClick={e => removeIngredient(ingredient)}
                    variant='outline-dark'
                    className="mb-1"
                >x</Button>
            </Col>
        </Row>
    )
}

AddIngredient.propTypes = {};

AddIngredient.defaultProps = {};

export default AddIngredient;
