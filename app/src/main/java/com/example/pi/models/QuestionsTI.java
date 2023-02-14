package com.example.pi.models;

public class QuestionsTI {

    public static String question[] = {
            ///Perguntas
            "O que é um mapa mental?",
            "Oque é um organograma?"
    };

    public static String choices[][] = {
            ///escolhas
            {"Um tipo de mapeamento para recrutar pessoas", "É um teste mental usado pelas organizações com seus colaboradores", "São diagramas que relacionam diferentes informações a uma fonte central", "Uma técnica de vendas"},
            {"Ilustra as etapas, sequências e decisões de um processo ou fluxo de trabalho", "Uma ilustração estratégica com o intuito de auxiliar clientes", "É uma representação gráfica da divisão hierárquica e da relação superior-subordinado", "Um gráfico com todos colaboradores de determinada organização"}
    };

    public static String correctAnswers[] = {
            ///respotas certas
            "São diagramas que relacionam diferentes informações a uma fonte central",
            "É uma representação gráfica da divisão hierárquica e da relação superior-subordinado"

    };

    public QuestionsTI(){}
}
