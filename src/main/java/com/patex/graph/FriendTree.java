package com.patex.graph;

import java.util.*;

/**
* Created by apotekhin on 12/12/2014.
*/
public class FriendTree {
    FriendTree parent;
    String _uid;
    Map<String, FriendTree> nodes = new HashMap<>();
    LeafExpander _leafExpander;

    public FriendTree(String uid,LeafExpander leafExpander) {
        _uid = uid;
        _leafExpander=leafExpander;
    }

    public FriendTree(FriendTree parent, String uid) {
        this.parent = parent;
        _uid = uid;
    }

    private void expandLeafs() {
        Set<FriendTree> leafs = getLeafs();
        for (FriendTree leaf : leafs) {
            String[] friends = _leafExpander.getRelated(leaf.getUid());
            if(friends.length<500) {
                HashSet<String> friendsSet = new HashSet<>(Arrays.asList(friends));
                for (String friend : friends) {
                    if (findNode(friend) != null) {
                        friendsSet.remove(friend);
                    }
                }
                leaf.update(friendsSet);
            }
        }
    }

    public Set<List<String>> findChains(FriendTree friendTreeTo) {
        Set<List<String>> intersectionChains = new HashSet<>();
        while (intersectionChains.isEmpty()) {
            expandLeafs();
            friendTreeTo.expandLeafs();
            intersectionChains.addAll(getIntersectionChains(friendTreeTo));
            if (!intersectionChains.isEmpty()) {
                break;
            }
        }
        return intersectionChains;
    }

    public void update(Collection<String> leafs) {
        for (String leaf : leafs) {
            nodes.put(leaf, new FriendTree(this, leaf));
        }
    }

    public Set<FriendTree> join(FriendTree chain) {
        return null;
    }

    private Set<FriendTree> getLeafs() {
        HashSet<FriendTree> leafs = new HashSet<>();
        if (nodes.isEmpty()) {
            leafs.add(this);
        } else {
            for (FriendTree node : nodes.values()) {
                leafs.addAll(node.getLeafs());
            }
        }
        return leafs;
    }

    private Set<List<String>> getIntersectionChains(FriendTree tree) {
        Set<List<String>> intersectionChains = new HashSet<>();
        final Set<FriendTree> leafs = tree.getLeafs();
        for (FriendTree leaf : leafs) {
            FriendTree node = findNode(leaf.getUid());
            if (node != null) {
                ArrayList<String> chain = new ArrayList<>();
                intersectionChains.add(chain);
                while (node != null) {
                    chain.add(node.getUid());
                    node = node.getParent();
                }
                leaf = leaf.getParent();
                while (leaf != null) {
                    chain.add(0, leaf.getUid());
                    leaf = leaf.getParent();
                }
            }
        }
        return intersectionChains;
    }

    private FriendTree findNode(String uid) {
        FriendTree chain = nodes.get(uid);
        if (chain != null) {
            return chain;
        } else if (nodes.isEmpty()) {
            return null;
        } else {
            for (FriendTree node : nodes.values()) {
                chain = node.findNode(uid);
                if (chain != null) {
                    return chain;
                }
            }
        }
        return null;
    }

    public String getUid() {
        return _uid;
    }

    public FriendTree getParent() {
        return parent;
    }
}
