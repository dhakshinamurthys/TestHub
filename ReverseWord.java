public class ReverseWord {
  public static void main(String[] args) {
    String word = "hello"; // Replace with your desired word
    // Reverse the word
    StringBuilder reversedWord = new StringBuilder();
    for (int i = word.length() - 1; i >= 0; i--) {
      reversedWord.append(word.charAt(i));
    }
    // Print the reversed word
    System.out.println(reversedWord);
  }
}
