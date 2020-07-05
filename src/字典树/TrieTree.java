package 字典树;

public class TrieTree {
    class TreeNode{
        char val;
        boolean isWord;
        TreeNode children[] = new TreeNode[26];
        TreeNode(){

        }
        TreeNode(char c) {
            TreeNode node = new TreeNode();
            node.val = c;
        }
    }

    /**
     * 208. 实现 Trie (前缀树)
     *
     * 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
     *
     * 示例:
     *
     * Trie trie = new Trie();
     *
     * trie.insert("apple");
     * trie.search("apple");   // 返回 true
     * trie.search("app");     // 返回 false
     * trie.startsWith("app"); // 返回 true
     * trie.insert("app");
     * trie.search("app");     // 返回 true
     *
     * 说明:
     *
     *     你可以假设所有的输入都是由小写字母 a-z 构成的。
     *     保证所有输入均为非空字符串。
     *
     * 通过次数42,476
     * 提交次数62,757
     */
    class Trie {
        private TreeNode root;
        public Trie() {
            this.root = new TreeNode();
            root.val = ' ';
        }
        public void insert(String word) {
            TreeNode ws = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (ws.children[c - 'a'] == null) {
                    ws.children[c - 'a'] = new TreeNode(c);
                }
                ws = ws.children[c - 'a'];
            }
            ws.isWord = true;
        }
        public boolean search(String word) {
            TreeNode ws = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (ws.children[c - 'a'] == null) {
                    return false;
                }
                ws = ws.children[c - 'a'];
            }
            return ws.isWord;
        }
        public boolean startsWith(String prefix) {
            TreeNode ws = root;
            for (int i = 0; i < prefix.length(); i++) {
                char c = prefix.charAt(i);
                if (ws.children[c - 'a'] == null) {
                    return false;
                }
                ws = ws.children[c - 'a'];
            }
            return true;
        }
    }


}
