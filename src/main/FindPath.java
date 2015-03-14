package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FindPath {

    int maxCol = 0;
    int maxRow = 0;

    private void main(String fileName) throws IOException {
	Bean[][] skiMap = readFile(fileName);
	List<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();
	for (int y = 1; y < maxRow - 1; y++) {
	    for (int x = 1; x < maxCol - 1; x++) {
		traverseMap(y, x, skiMap, new LinkedList<Integer>(), result);
	    }
	}
	sortAndPrintResult(result);
    }

    private int calculateSlope(LinkedList<Integer> que) {

	return que.getLast() - que.getFirst();
    }

    private void sortAndPrintResult(List<LinkedList<Integer>> result) {
	Collections.sort(result, new Comparator<LinkedList<Integer>>() {

	    @Override
	    public int compare(LinkedList<Integer> firstQueue,
		    LinkedList<Integer> secondQueue) {

		if (secondQueue.size() == firstQueue.size()) {
		    return calculateSlope(secondQueue)
			    - calculateSlope(firstQueue);
		} else {
		    return secondQueue.size() - firstQueue.size();
		}

	    }
	});

	int line = 0;
	for (LinkedList<Integer> que : result) {
	    StringBuffer sb = new StringBuffer();
	    int len = que.size();
	    int slope = calculateSlope(que);
	    while (!que.isEmpty()) {
		sb.append(que.pollLast()).append(",");
	    }
	    System.out.println("length=" + len + ",slope=" + slope
		    + ", result=" + sb.toString());

	    line++;
	    if (line > 5) {
		return;
	    }
	}

    }

    private void traverseMap(int curY, int curX, Bean[][] skiMap,
	    LinkedList<Integer> stack, List<LinkedList<Integer>> result) {
	// int curVal = skiMap[curY][curX].getVal();
	Bean bean = skiMap[curY][curX];
	bean.setVisited(true);
	stack.push(bean.getVal());
	// System.out.println(curY + "," + curX);
	// if (curX <= 0 || curY <= 0 || curX >= maxCol - 1 || curY >= maxRow -
	// 1)

	if (!bean.isVisitable()) {
	    LinkedList<Integer> stackClone = new LinkedList<Integer>();

	    stackClone.addAll(stack);
	    stackClone.pop();
	    result.add(stackClone);

	} else {

	    if ((skiMap[curY][curX - 1].getVal() < bean.getVal())
		    && (!skiMap[curY][curX - 1].isVisited())) {
		traverseMap(curY, curX - 1, skiMap, stack, result);
	    }// left;
	    if (skiMap[curY][curX + 1].getVal() < bean.getVal()
		    && (!skiMap[curY][curX + 1].isVisited())) {
		traverseMap(curY, curX + 1, skiMap, stack, result);
	    }// right;
	    if (skiMap[curY - 1][curX].getVal() < bean.getVal()
		    && (!skiMap[curY - 1][curX].isVisited())) {
		traverseMap(curY - 1, curX, skiMap, stack, result);
	    }// up;
	    if (skiMap[curY + 1][curX].getVal() < bean.getVal()
		    && (!skiMap[curY + 1][curX].isVisited())) {
		traverseMap(curY + 1, curX, skiMap, stack, result);
	    }// down;
	}
	bean.setVisited(false);
	stack.pop();
    }

    private Bean[][] generateMap(int row, int col) {
	Bean[][] result = new Bean[row][col];
	for (int y = 0; y < row; y++) {
	    for (int x = 0; x < col; x++) {
		Bean bean = new Bean();
		bean.setVal(0);
		bean.setVisitable(false);
		bean.setVisited(false);
		result[y][x] = bean;
	    }
	}
	return result;
    }

    private Bean[][] readFile(String filename) throws IOException {
	File file = new File(filename);
	try (BufferedReader br = new BufferedReader(new FileReader(file))) {

	    {
		String line = br.readLine();
		String[] arrNumber = line.split(" ");
		maxRow = Integer.parseInt(arrNumber[0]) + 2;
		maxCol = Integer.parseInt(arrNumber[1]) + 2;
	    }
	    int row = 0;
	    Bean[][] result = generateMap(maxRow, maxCol);

	    for (String line; (line = br.readLine()) != null; row++) {
		String[] arrNumber = line.split(" ");

		for (int col = 0; col < arrNumber.length; col++) {
		    Bean bean = result[1 + row][1 + col];
		    bean.setVal(Integer.parseInt(arrNumber[col]));
		    bean.setVisited(false);
		    bean.setVisitable(true);
		}
	    }
	    return result;
	}

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

	try {
	    new FindPath().main(args[0]);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
