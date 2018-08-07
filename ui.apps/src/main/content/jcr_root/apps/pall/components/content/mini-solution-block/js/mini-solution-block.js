var miniSolutionBlock = {

    init: function() {
        $('.mini-solution-block__container__solution__icon').hover(function() {
            var bgColor = $(this).data('bgcolor');
            $(this).removeClass('bg-darken-' + bgColor);
            $(this).addClass('bg-' + bgColor);
        }, function() {
            var bgColor = $(this).data('bgcolor');
            $(this).removeClass('bg-' + bgColor);
            $(this).addClass('bg-darken-' + bgColor);
        });

        $firstMiniSolution = $('.mini-solution-block__container').first();
        $firstMiniSolution
            .find('.mini-solution-block__container__solution').toggleClass('first-block');
    },

    needToInit: function() {
        return $('.mini-solution-block').length > 0;
    }
};