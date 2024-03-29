#!/usr/bin/make -f

source=French
target=Chinese

src=$(shell echo $(source) | tr A-Z a-z)
tgt=$(shell echo $(target) | tr A-Z a-z)

code=zh
srcCode=fr
tgtCode=$(code)

spotter=CaseNormalizer.xml
spotter+=MoodNormalizer.xml
spotter+=Stemmer.xml
spotter+=TreeTagger.xml
spotter+=CategoryNormalizer.xml
spotter+=MultiWordTermSpotter.xml
spotter+=SubcategoryNormalizer.xml
spotter+=WordTokenizer.xml
spotter+=Filter.xml
spotter+=Normalizer.xml
spotter+=TenseNormalizer.xml
spotter+=GenderNormalizer.xml
spotter+=NumberNormalizer.xml
spotter+=TermFilter.xml
spotter+=LemmaFilter.xml
spotter+=Spotter.xml
spotter+=TermSpotter.xml

indexer=CompoundIndexer.xml
indexer+=Indexer.xml
indexer+=NeoclassicalSplitter.xml
indexer+=PrefixSplitter.xml
indexer+=TermGatherer.xml
indexer+=CompoundSplitter.xml
indexer+=MultiWordTermGatherer.xml
indexer+=TermFrequencyComputer.xml

indexerTargets = $(addprefix $(tgt)/engines/indexer/$(target),$(indexer))
spotterTargets = $(addprefix $(tgt)/engines/spotter/$(target),$(spotter))

indexSources = $(addprefix $(src)/engines/indexer/$(source),$(indexer))
spotterSources = $(addprefix $(src)/engines/spotter/$(source),$(spotter))

xmlResources =case-mapping.xml
xmlResources+=category-mapping.xml
xmlResources+=gender-mapping.xml
xmlResources+=mood-mapping.xml
xmlResources+=multi-word-rule-system.xml
xmlResources+=number-mapping.xml
xmlResources+=segment-bank.xml
xmlResources+=stop-word-filter.xml
xmlResources+=subcategory-mapping.xml
xmlResources+=tense-mapping.xml
xmlResources+=treetagger.xml
xmlResources+=variant-rule-system.xml

txtResources =GeneralLanguage
txtResources+=Prefix
txtResources+=RootBank

help: 
	@echo "make copy target=<Uppercase directory> code=<2-letter language code>"

copy: $(indexerSources) $(spotterSources)
	@mkdir -p $(tgt)/engines/indexer
	@for file in $(indexer) ; do make -s copy-indexer file=$$file ; done
	@mkdir -p $(tgt)/engines/spotter
	@for file in $(spotter) ; do make -s copy-spotter file=$$file ; done

copy-indexer: $(addprefix $(src)/engines/indexer/$(source),$(file))
	@cp $(addprefix $(src)/engines/indexer/$(source),$(file)) $(addprefix $(tgt)/engines/indexer/$(target),$(file))
	@make -s edit-indexer file=$(file)

copy-spotter: $(addprefix $(src)/engines/spotter/$(source),$(file))
	@cp $(addprefix $(src)/engines/spotter/$(source),$(file)) $(addprefix $(tgt)/engines/spotter/$(target),$(file))
	@make -s edit-spotter file=$(file)

edit-indexer: $(addprefix $(tgt)/engines/indexer/$(target),$(file))
	@sed -i 's/$(source)/$(target)/g' $(addprefix $(tgt)/engines/indexer/$(target),$(file))
	@sed -i 's/$(src)/$(tgt)/g' $(addprefix $(tgt)/engines/indexer/$(target),$(file))
	@sed -i 's/language>$(srcCode)/language>$(tgtCode)/g' $(addprefix $(tgt)/engines/indexer/$(target),$(file))
	@sed -i 's/string>$(srcCode)/string>$(tgtCode)/g' $(addprefix $(tgt)/engines/indexer/$(target),$(file))

edit-spotter: $(addprefix $(tgt)/engines/spotter/$(target),$(file))
	@sed -i 's/$(source)/$(target)/g' $(addprefix $(tgt)/engines/spotter/$(target),$(file))
	@sed -i 's/$(src)/$(tgt)/g' $(addprefix $(tgt)/engines/spotter/$(target),$(file))
	@sed -i 's/language>$(srcCode)/language>$(tgtCode)/g' $(addprefix $(tgt)/engines/spotter/$(target),$(file))
	@sed -i 's/string>$(srcCode)/string>$(tgtCode)/g' $(addprefix $(tgt)/engines/spotter/$(target),$(file))

plug: $(addprefix $(src)/resources/$(src)-, $(xmlResources)) $((addprefix $(src)/resources/, $(txtResources)):=.$(source))
	@mkdir -p $(tgt)/resources
	@for file in $(xmlResources) ; do make -s xml-resource file=$$file ; done
	@for file in $(txtResources) ; do make -s txt-resource file=$$file ; done

xml-resource: $(addprefix $(src)/resources/$(src)-, $(file))
	@cp $(addprefix $(src)/resources/$(src)-, $(file)) $(addprefix $(tgt)/resources/$(tgt)-, $(file))

txt-resource: $(addprefix $(src)/resources/, $(file)).$(source)
	@cp $(addprefix $(src)/resources/, $(file)).$(source) $(addprefix $(tgt)/resources/, $(file)).$(target)
