public class Lab01P3Wrapper {

    public static class Sports {
        private String name;
        private int players;
        public String testValue; //DO NOT DELETE THIS

        public Sports(String name, int players) {
            this.name = name;
            this.players = players;
        }

        public String getName() {
            return name;
        }

        public int getPlayers() {
            return players;
        }

        public String printTeamMembers() {
            return "Each team has " + getPlayers() + " players in " + getName();
        }

        //DO NOT DELETE THIS
        public void setTestValue() {
            testValue = printTeamMembers();
        }
    }

    public static class Soccer extends Sports {

        public Soccer(String name, int players) {
            super(name, players);
        }

        public String getName(){
            return "Overwritten Soccer";
        }

        public int getPlayers(){
            return 11;
        }

    }
}
