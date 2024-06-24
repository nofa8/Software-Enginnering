package Modelos;
//LUIS
public enum Editora {
    PENGUIN_RANDOM_HOUSE,
    HARPERCOLLINS,
    SIMON_AND_SCHUSTER,
    HACHETTE_BOOK_GROUP,
    MACMILLAN_PUBLISHERS,
    SCHOLASTIC,
    JOHN_WILEY_AND_SONS,
    OXFORD_UNIVERSITY_PRESS,
    CAMBRIDGE_UNIVERSITY_PRESS,
    PEARSON_EDUCATION;

    public String getNome() {
        switch (this) {
            case PENGUIN_RANDOM_HOUSE: return "Penguin Random House";
            case HARPERCOLLINS: return "HarperCollins";
            case SIMON_AND_SCHUSTER: return "Simon & Schuster";
            case HACHETTE_BOOK_GROUP: return "Hachette Book Group";
            case MACMILLAN_PUBLISHERS: return "Macmillan Publishers";
            case SCHOLASTIC: return "Scholastic";
            case JOHN_WILEY_AND_SONS: return "John Wiley & Sons";
            case OXFORD_UNIVERSITY_PRESS: return "Oxford University Press";
            case CAMBRIDGE_UNIVERSITY_PRESS: return "Cambridge University Press";
            case PEARSON_EDUCATION: return "Pearson Education";
            default: throw new IllegalArgumentException("Editora desconhecida: " + this);
        }
    }
}