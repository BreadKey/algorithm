package programmers.hash.phonebook;

public class Solution {
    public boolean solution(String[] phone_book) {
        for (final String prefix : phone_book) {
            final int hashcode = prefix.hashCode();

            for (String phoneNumber : phone_book) {
                if (prefix.length() < phoneNumber.length()) {
                    if (phoneNumber.substring(0, prefix.length()).hashCode() == hashcode) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}