package com.abdullah.calculator.Math;

public abstract class TypeExp {
    public static TypeExp getType(String symbol, double a, double b) {
        switch (symbol) {
            case "^":
                return new POWER(a, b);
            case "*":
                return new MULTI(a, b);
            case "/":
                return new DIV(a, b);
            case "%":
                return new REMAINDER(a, b);
            case "+":
                return new SUM(a, b);
            case "-":
                return new MINUS(a, b);
            default:
                return new UnKnown();
        }
    }

    public double getValue() {
        return 0.0;
    }

    public static class MULTI extends TypeExp {
        private final double a;
        private final double b;

        public MULTI(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public double getValue() {
            return a * b;
        }
    }

    public static class DIV extends TypeExp {
        private final double a;
        private final double b;

        public DIV(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public double getValue() {
            return a / b;
        }
    }

    public static class SUM extends TypeExp {
        private final double a;
        private final double b;

        public SUM(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public double getValue() {
            return a + b;
        }
    }

    public static class MINUS extends TypeExp {
        private final double a;
        private final double b;

        public MINUS(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public double getValue() {
            return a - b;
        }
    }

    public static class POWER extends TypeExp {
        private final double a;
        private final double b;

        public POWER(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public double getValue() {
            return Math.pow(a, b);
        }
    }

    public static class REMAINDER extends TypeExp {
        private final double a;
        private final double b;

        public REMAINDER(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public double getValue() {
            return a % b;
        }
    }

    public static class UnKnown extends TypeExp {
        @Override
        public double getValue() {
            return 0.0;
        }
    }
}