package Modelos;
//LUIS
public enum Distribuidor {
    INGRAM_CONTENT_GROUP,
    BAKER_AND_TAYLOR,
    PERSEUS_BOOKS_GROUP,
    FOLLETT,
    MIDPOINT_TRADE_BOOKS,
    INDEPENDENT_PUBLISHERS_GROUP,
    NATIONAL_BOOK_NETWORK,
    IPG_BOOKS,
    MACMILLAN_DISTRIBUTION,
    SIMON_AND_SCHUSTER_DISTRIBUTION,
    HACHETTE_DISTRIBUTION,
    RANDOM_HOUSE_DISTRIBUTION,
    BOOKMASTERS,
    ANONIMO,
    LEYA,
    GRUPO_PORTO_EDITORA,
    BERTRAND,
    GRADIVA,
    ALMEDINA;

    public String getNome() {
        switch (this) {
            case INGRAM_CONTENT_GROUP: return "Ingram Content Group";
            case BAKER_AND_TAYLOR: return "Baker & Taylor";
            case PERSEUS_BOOKS_GROUP: return "Perseus Books Group";
            case FOLLETT: return "Follett";
            case MIDPOINT_TRADE_BOOKS: return "Midpoint Trade Books";
            case INDEPENDENT_PUBLISHERS_GROUP: return "Independent Publishers Group";
            case NATIONAL_BOOK_NETWORK: return "National Book Network";
            case IPG_BOOKS: return "IPG Books";
            case MACMILLAN_DISTRIBUTION: return "Macmillan Distribution";
            case SIMON_AND_SCHUSTER_DISTRIBUTION: return "Simon & Schuster Distribution";
            case HACHETTE_DISTRIBUTION: return "Hachette Distribution";
            case RANDOM_HOUSE_DISTRIBUTION: return "Random House Distribution";
            case BOOKMASTERS: return "Bookmasters";
            case ANONIMO: return "Anônimo";
            case LEYA: return "Leya";
            case GRUPO_PORTO_EDITORA: return "Grupo Porto Editora";
            case BERTRAND: return "Bertrand";
            case GRADIVA: return "Gradiva";
            case ALMEDINA: return "Almedina";
            default: throw new IllegalArgumentException("Distribuidor desconhecido: " + this);
        }
    }
}
