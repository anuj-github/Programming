package com.programs.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TrieProblems {

    static HashMap<Integer, Character[]> numberMap;

    private static void populateNumberMap(){
        numberMap = new HashMap<>();
        numberMap.put(0, null);
        numberMap.put(1, null);
        numberMap.put(2, new Character[]{'a','b','c'});
        numberMap.put(3, new Character[]{'d','e','f'});
        numberMap.put(4, new Character[]{'g','h','i'});
        numberMap.put(5, new Character[]{'j','k','l'});
        numberMap.put(6, new Character[]{'m','n','o'});
        numberMap.put(7, new Character[]{'p','q','r','s'});
        numberMap.put(8, new Character[]{'t','u','v'});
        numberMap.put(9, new Character[]{'w','x','y','z'});
    }

    public  static  void main(String[] args){

        Trie trie = new Trie();
        trie.insert("hack");
        trie.insert("hackerrank");
        System.out.println("Item size "+ prefixSearch(trie.root, "hac"));
      // populateNumberMap();
      // System.out.print(findWords(trie, "8733"));

    }


    public static int prefixSearch(TrieNode root,String prefix){
        if(prefix==null||prefix.trim().length()==0) return 0;
        TrieNode current = root;
        for(int i = 0; i < prefix.length();i++){
            int index = prefix.charAt(i)-'a';
            TrieNode temp = current.children[index];
            if(temp==null) return 0;
            current = temp;
        }
        if(!current.isWord) return current.count;
        return current.count+1;
    }

    private static Set<String> findWords(Trie trie, String num){
        if(trie==null || num==null || num.isEmpty()) return  null;
        Set<String> set = new HashSet<>();
         findWords(trie.root, num, set, "",0);
         return  set;
    }

    private static void findWords(TrieNode trie, String num, Set<String> set, String prefix, int index) {
        if(index== num.length()){
            if(trie.isWord){
                set.add(prefix);
            }
            return;
        }

        int n = Character.getNumericValue(num.charAt(index));
        Character[] arr = getT9Char(n);
        if(arr!=null){
            for(char c : arr){
                int i = c-'a';
                if(trie.children[i]!=null){
                    TrieNode child = trie.children[i];
                    findWords(child,num,set,prefix+c, index+1);
                }
            }
        }
    }

    private static Character[] getT9Char(int n) {

        return  numberMap.get(n);
    }






}
