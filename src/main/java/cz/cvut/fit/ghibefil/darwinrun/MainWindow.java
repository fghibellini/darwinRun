/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import java.awt.ComponentOrientation;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jbox2d.common.Vec2;


/**
 *
 * @author ghibe
 */
public class MainWindow extends javax.swing.JFrame {
    static int SIMULATION_HEIGHT = 3;
    static int SIMULATION_WIDTH = 5;
    
    Simulation sim;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        pack();
        
        sim = new Simulation();    
                
        renderPanel1.setSimulation(sim);
        renderPanel1.addDrawable(new AthleteRenderer(sim.getRunner()));        
        setVisible(true);        
        renderPanel1.initStrategy();
                
        ArrayList<ArrayList<Genotype>> genotypes = new ArrayList<>();
        ArrayList<Genotype> generation1 = new ArrayList();
        generation1.add(new JumpingGenotype(0f));
        generation1.add(new JumpingGenotype(1f/50f));
        generation1.add(new JumpingGenotype(3f/50f));
        genotypes.add(generation1);
        
        setupTreeview(genotypes);
        
        applyGenotype(genotypes.get(0).get(0));
        
        new Thread(sim, "simulation1").start();
    }
    
    public void applyGenotype(Genotype gen) {
        Puppeteer puppeteer = new JumpingPuppeteer(gen);
        
        sim.setPuppeteer(puppeteer);
    }
    
    public static void main(String[] args) {
        new MainWindow();
    }
    
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        renderPanel1 = new cz.cvut.fit.ghibefil.darwinrun.RenderPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        genotypeTreeView = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DarwinRun");

        jPanel1.setBackground(new java.awt.Color(0, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(renderPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(renderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        genotypeTreeView.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                genotypeTreeViewValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(genotypeTreeView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void genotypeTreeViewValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_genotypeTreeViewValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) genotypeTreeView.getLastSelectedPathComponent();
        if (node == null || !node.isLeaf()) return;
        Genotype selectedGenotype = (Genotype) node.getUserObject();
        applyGenotype(selectedGenotype);
    }//GEN-LAST:event_genotypeTreeViewValueChanged

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree genotypeTreeView;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private cz.cvut.fit.ghibefil.darwinrun.RenderPanel renderPanel1;
    // End of variables declaration//GEN-END:variables

    private void setupTreeview(ArrayList<ArrayList<Genotype>> genotypes) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("genotypes");
        
        int i = 0;
        for (ArrayList<Genotype> generation : genotypes) {
            DefaultMutableTreeNode generationNode = new DefaultMutableTreeNode("generation " + ++i);
            top.add(generationNode);
            
            for (Genotype gene : generation) {
                DefaultMutableTreeNode genotypeNode = new DefaultMutableTreeNode(gene);
                generationNode.add(genotypeNode);
            }
        }
        
        DefaultTreeModel treeModel = new DefaultTreeModel(top);
        genotypeTreeView.setModel(treeModel);
    }
}
