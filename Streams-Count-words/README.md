# Streams. Count words

## Description
Count words in a text and return statistics.

## Details
Implement `countWords` method in [Words](src/main/java/com/epam/rd/autotasks/Words.java) class.

Input parameter is a list of strings representing lines of text.

You must count, how many times each word appears int the text and then return the statistics.
If the word *"kitten"* occurred in a text 23 times then its entry would be *"kitten - 23\n"*.
Return statistics as a String containing all the entries.

Omit any word having length less than **4** and frequency less than **10** (consider them too small or too rare)
Entries in the resulting String must be sorted by amount and then in alphabetical order if needed.

Be sure to make your code handling texts that are not in English.

*You may not use conditional statements and cycles in your code (that is right, no **if**, **for**, **while**, etc.)*