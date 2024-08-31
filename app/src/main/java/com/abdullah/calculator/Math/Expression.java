package com.abdullah.calculator.Math;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private final String equation;
    private final String numExp = "([-+]?[0-9]+\\.?[0-9]*)";
    private String result = "";

    public Expression(String equation) {
        this.equation = equation;
    }

    public String getResult() {
        return result;
    }

    public void evaluate() {
        if (result.isEmpty()) {
            result = _evaluate(equation);
        }
    }

    private String _evaluate(String subExp) {
        String cleanedExp = cleanExp(subExp);
        if (!subExp.equals(cleanedExp)) {
            return _evaluate(cleanedExp);
        }

        Matcher groupMatcher = Pattern.compile("\\([0-9.+\\-*/^%]+\\)").matcher(subExp);
        Matcher powExpMatcher = Pattern.compile(numExp + "(\\^)" + numExp).matcher(subExp);
        Matcher multiExpMatcher = Pattern.compile(numExp + "(\\*)" + numExp).matcher(subExp);
        Matcher divExpMatcher = Pattern.compile(numExp + "(/)" + numExp).matcher(subExp);
        Matcher remExpMatcher = Pattern.compile(numExp + "(%)" + numExp).matcher(subExp);
        Matcher plusExpMatcher = Pattern.compile(numExp + "(\\+)" + numExp).matcher(subExp);
        Matcher minusExpMatcher = Pattern.compile(numExp + "(-)" + numExp).matcher(subExp);

        List<MatchResult> expressions = new ArrayList<>();

        if (groupMatcher.find()) {
            int start = groupMatcher.start();
            int end = groupMatcher.end();
            String rangeSubExp = subExp.substring(start + 1, end - 1);
            String evaluatedRange = _evaluate(rangeSubExp);
            return _evaluate(subExp.substring(0, start) + evaluatedRange + subExp.substring(end));
        }

        if (powExpMatcher.find()) {
            return _evaluate(subExp.substring(0, powExpMatcher.start()) +
                    calcExp(powExpMatcher.toMatchResult()) +
                    subExp.substring(powExpMatcher.end()));
        }

        if (divExpMatcher.find()) expressions.add(divExpMatcher.toMatchResult());
        if (multiExpMatcher.find()) expressions.add(multiExpMatcher.toMatchResult());
        if (remExpMatcher.find()) expressions.add(remExpMatcher.toMatchResult());

        expressions.sort((o1, o2) -> Integer.compare(o1.start(), o2.start()));

        if (expressions.isEmpty()) {
            if (plusExpMatcher.find()) expressions.add(plusExpMatcher.toMatchResult());
            if (minusExpMatcher.find()) expressions.add(minusExpMatcher.toMatchResult());
            expressions.sort((o1, o2) -> Integer.compare(o1.start(), o2.start()));
        }

        if (!expressions.isEmpty()) {
            MatchResult firstExpression = expressions.get(0);
            return _evaluate(subExp.substring(0, firstExpression.start()) +
                    calcExp(firstExpression) +
                    subExp.substring(firstExpression.end()));
        }

        try {
            return Double.toString(Double.parseDouble(subExp));
        } catch (Exception e) {
            throw new ArithmeticException("Invalid Syntax: " + e.getMessage());
        }
    }

    private String calcExp(MatchResult exp) {
        double leftOperand = Double.parseDouble(exp.group(1));
        String operator = exp.group(2);
        double rightOperand = Double.parseDouble(exp.group(3));

        double value;
        switch (operator) {
            case "^":
                value = Math.pow(leftOperand, rightOperand);
                break;
            case "*":
                value = leftOperand * rightOperand;
                break;
            case "/":
                value = leftOperand / rightOperand;
                break;
            case "%":
                value = leftOperand % rightOperand;
                break;
            case "+":
                value = leftOperand + rightOperand;
                break;
            case "-":
                value = leftOperand - rightOperand;
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
        return value > 0 ? "+" + value : Double.toString(value);
    }

    private String cleanExp(String e) {
        if (e.contains(" ")) {
            return cleanExp(e.replace(" ", ""));
        }

        List<Integer> signsPos = new ArrayList<>();
        int index = e.indexOf("++");
        if (index != -1) signsPos.add(index);
        index = e.indexOf("--");
        if (index != -1) signsPos.add(index);
        index = e.indexOf("+-");
        if (index != -1) signsPos.add(index);
        index = e.indexOf("-+");
        if (index != -1) signsPos.add(index);

        signsPos.sort(Integer::compare);

        if (signsPos.isEmpty()) {
            Matcher matcher = Pattern.compile("[0-9]\\(").matcher(e);
            if (matcher.find()) {
                int pos = matcher.start();
                return cleanExp(e.substring(0, pos + 1) + "*" + e.substring(pos + 1));
            }

            matcher = Pattern.compile("\\)[0-9]").matcher(e);
            if (matcher.find()) {
                int pos = matcher.start();
                return cleanExp(e.substring(0, pos + 1) + "*" + e.substring(pos + 1));
            }

            return e;
        } else {
            int pos = signsPos.get(0);
            String replacement;
            switch (e.substring(pos, pos + 2)) {
                case "++":
                case "--":
                    replacement = "+";
                    break;
                case "+-":
                case "-+":
                    replacement = "-";
                    break;
                default:
                    replacement = e.substring(pos, pos + 2);
                    break;
            }
            return cleanExp(e.substring(0, pos) + replacement + e.substring(pos + 2));
        }
    }
}