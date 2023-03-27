package com.lucas.restspringboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.restspringboot.exceptions.UnsupportedMathOperationException;
import com.lucas.restspringboot.services.MathService;
import com.lucas.restspringboot.shared.NumberConverter;

@RestController
public class MathController {
    MathService mathService = new MathService();

    @GetMapping(value = "/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }

        Double convertedNumberOne = NumberConverter.convertToDouble(numberOne);
        Double convertedNumberTwo = NumberConverter.convertToDouble(numberTwo);
        return mathService.sum(convertedNumberOne, convertedNumberTwo);
    }

    @GetMapping(value = "/sub/{numberOne}/{numberTwo}")
    public Double sub(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }

        Double convertedNumberOne = NumberConverter.convertToDouble(numberOne);
        Double convertedNumberTwo = NumberConverter.convertToDouble(numberTwo);
        return mathService.sub(convertedNumberOne, convertedNumberTwo);
    }

    @GetMapping(value = "/mul/{numberOne}/{numberTwo}")
    public Double mul(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        Double convertedNumberOne = NumberConverter.convertToDouble(numberOne);
        Double convertedNumberTwo = NumberConverter.convertToDouble(numberTwo);
        return mathService.mul(convertedNumberOne, convertedNumberTwo);
    }
}
