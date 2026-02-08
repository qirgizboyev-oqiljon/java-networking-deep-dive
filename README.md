# java-networking-deep-dive

A hands-on exploration of Java networking internals by building servers from scratch,
observing real TCP behavior, and identifying bottlenecks through experiments.

## Structure

- **01-tcp-blocking-server**  
  Blocking TCP server implementation with acceptor/worker architecture and
  real bottleneck analysis using metrics and logs.

## Motivation

This repository is not a collection of tutorials.
It is an engineering notebook documenting how low-level networking actually behaves
under load.

Each folder represents a focused experiment.