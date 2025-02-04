/* jshint esversion: 6 */
/* globals describe, it, expect, jest, document, ReactDOM */
(function () {
    "use strict";

    import React from 'react';
    import ReactDOM from 'react-dom';
    import axios from 'axios';
    import PollApp from './PollApp';

    describe('PollApp', () => {
        it('renders poll question and options', () => {
            const poll = {
                question: 'What is your favorite color?',
                options: ['Red', 'Blue', 'Green']
            };

            const tree = ReactDOM.createPortal(
                <PollApp poll={poll}/>,
                document.getElementById('root')
            );

            expect(tree).toMatchSnapshot();
        });

        it('renders loading message when polling is not available', () => {
            const poll = {};

            const tree = ReactDOM.createPortal(
                <PollApp poll={poll}/>,
                document.getElementById('root')
            );

            expect(tree).toMatchSnapshot();
        });

        it('renders results when polling is available', () => {
            const poll = {
                question: 'What is your favorite color?',
                options: ['Red', 'Blue', 'Green'],
                results: {
                    Red: 30,
                    Blue: 40,
                    Green: 30
                }
            };

            const tree = ReactDOM.createPortal(
                <PollApp poll={poll}/>,
                document.getElementById('root')
            );

            expect(tree).toMatchSnapshot();
        });

        it('renders voting button when options are selected', () => {
            const poll = {
                question: 'What is your favorite color?',
                options: ['Red', 'Blue', 'Green'],
                selectedOption: 1
            };

            const tree = ReactDOM.createPortal(
                <PollApp poll={poll}/>,
                document.getElementById('root')
            );

            expect(tree).toMatchSnapshot();
        });

        it('makes API request to fetch poll data', () => {
            const mockAxiosGet = jest.fn(() => Promise.resolve({
                data: {
                    question: 'What is your favourite colour?',
                    options: ['Red', 'Blue', 'Green']
                }
            }));
            axios.get = mockAxiosGet;

            const tree = ReactDOM.createPortal(
                <PollApp/>,
                document.getElementById('root')
            );

            expect(axios.get).toHaveBeenCalledTimes(1);
        });

        it('makes API request to submit vote', () => {
            const mockAxiosPost = jest.fn(() => Promise.resolve({data: {results: {Red: 30, Blue: 40, Green: 30}}}));
            axios.post = mockAxiosPost;

            const tree = ReactDOM.createPortal(
                <PollApp/>,
                document.getElementById('root')
            );

            expect(axios.post).toHaveBeenCalledTimes(1);
        });
    });
})();